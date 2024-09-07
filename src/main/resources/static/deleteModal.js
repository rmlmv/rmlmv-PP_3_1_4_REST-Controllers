document.addEventListener('DOMContentLoaded', function() {
    const deleteButtons = document.querySelectorAll('.delete-btn');
    deleteButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            const row = this.closest('tr');

            document.getElementById('id-del').value = row.querySelector('.user-id').textContent;
            document.getElementById('first-name-del').value = row.querySelector('.user-first-name').textContent;
            document.getElementById('last-name-del').value = row.querySelector('.user-last-name').textContent;
            document.getElementById('age-del').value = row.querySelector('.user-age').textContent;
            document.getElementById('email-del').value = row.querySelector('.user-email').textContent;
        });
    });
});