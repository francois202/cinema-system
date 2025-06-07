function sortSessionsByDate(order = 'asc') {
    const rows = Array.from(document.querySelectorAll('table tbody tr'));

    rows.sort((a, b) => {
        const dateA = new Date(a.cells[3].textContent);
        const dateB = new Date(b.cells[3].textContent);
        return order === 'asc' ? dateA - dateB : dateB - dateA;
    });

    const tbody = document.querySelector('table tbody');
    tbody.innerHTML = '';
    rows.forEach(row => tbody.appendChild(row));
}

function addSortButtons() {
    const tableHeader = document.querySelector('table thead tr');
    const dateHeader = tableHeader.children[3];

    dateHeader.innerHTML = `
        <div style="display: flex; align-items: center; gap: 5px;">
            <span>Дата и время</span>
            <button class="sort-btn" data-order="asc" title="Сортировать по возрастанию">
                <i class="bi bi-arrow-up"></i>
            </button>
            <button class="sort-btn" data-order="desc" title="Сортировать по убыванию">
                <i class="bi bi-arrow-down"></i>
            </button>
        </div>
    `;

    document.querySelectorAll('.sort-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const order = btn.getAttribute('data-order');
            sortSessionsByDate(order);
        });
    });
}

document.addEventListener('DOMContentLoaded', function() {
    addSortButtons();
    fetch('/sessions/stats')
        .then(response => response.json())
        .then(data => {
            const labels = data.map(item => item[0]);
            const counts = data.map(item => item[1]);

            const ctx = document.getElementById('sessionsChart').getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Sessions per day',
                        data: counts,
                        backgroundColor: 'rgba(54, 162, 235, 0.5)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        y: {
                            beginAtZero: true,
                            precision: 0,
                            ticks: {
                                stepSize: 1
                            }
                        },
                        x: {
                            ticks: {
                                autoSkip: true,
                                maxRotation: 45,
                                minRotation: 45
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            display: false
                        }
                    }
                }
            });
        });

    const counter = document.querySelector('.counter span');
    if (counter) {
        const count = document.querySelectorAll('table tbody tr').length;
        counter.textContent = count;
    }
});