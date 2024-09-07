document.addEventListener('DOMContentLoaded', function() {
    const editButtons = document.querySelectorAll('.edit-btn');

    editButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            const row = this.closest('tr');

            document.getElementById('id-upd').value = row.querySelector('.user-id').textContent;
            document.getElementById('first-name-upd').value
                    = row.querySelector('.user-first-name').textContent;
            document.getElementById('last-name-upd').value = row.querySelector('.user-last-name').textContent;
            document.getElementById('age-upd').value = row.querySelector('.user-age').textContent;
            document.getElementById('email-upd').value = row.querySelector('.user-email').textContent;
            document.getElementById('password-upd').value = row.querySelector('.user-password').value;
        });
    });

    if (document.getElementById('has-errors').value === 'true') {
        document.getElementById('password-upd').value
                = document.getElementById('password-upd-val').value;
        new bootstrap.Modal(document.getElementById('upd-user-modal')).show();
    }
});