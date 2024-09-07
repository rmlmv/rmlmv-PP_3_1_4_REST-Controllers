document.addEventListener('DOMContentLoaded', function() {
    if (document.getElementById('has-errors').value === 'true') {
        document.getElementById('password-save').value
                = document.getElementById('password-save-val').value;
    }
});