const apiUrl = '/api/users';
const simpleUserFields = ['firstName', 'lastName', 'age', 'email', 'password'];

$(() => {
    $('#add-btn').on('click', async () => {
        $('.add-form .alert-danger').hide();

        const userToAdd = buildUserJson();

        console.log(userToAdd);

        const response = await fetch(apiUrl, {
            method: "POST",
            body: JSON.stringify(userToAdd),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            window.location.href = '/admin';
        } else {
            const validationErrors = (await response.json()).validationErrors;
            if (validationErrors != null) {
                Object.entries(validationErrors).forEach(([field, message]) => {
                    console.log(field);
                    console.log(message);
                    $(`.add-form .alert-danger.${field}`).text(message).show();
                });
            }
        }

    });
})

function buildUserJson() {
    const user = {};

    simpleUserFields.forEach((field) => user[field] = $(`.add-form .form-control.${field}`).val());

    user['roles'] = $('.add-form option:selected').map(function () {
        return { id: parseInt($(this).val()) };
    }).get();

    return user;
}