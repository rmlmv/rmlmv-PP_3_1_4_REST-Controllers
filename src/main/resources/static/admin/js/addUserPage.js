const usersApiUrl = '/api/users';
const rolesApiUrl = '/api/roles';
const simpleUserFields = ['firstName', 'lastName', 'age', 'email', 'password'];
let rolesList;

$(async () => {

    const mePromise = fetchAndParseJson(`${usersApiUrl}/me`);
    mePromise.then(me => {
        $('#top-bar .user-email').text(me.email);
        const rolesSpan = $('#top-bar .user-roles');
        me.roles.forEach(role => rolesSpan.append(
            ' ' + (role.name.startsWith('ROLE_') ? role.name.substring(5) : role.name)
        ));
    });

    $('#add-btn').on('click', async () => {
        $('.add-form .alert-danger').hide();

        const userToAdd = buildUserJson();

        console.log(userToAdd);

        const response = await fetch(usersApiUrl, {
            method: "POST",
            body: JSON.stringify(userToAdd),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            window.location.href = '/admin/admin.html';
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

    rolesList = await fetchAndParseJson(rolesApiUrl);

    const select = $('.add-form .form-select.roles');
    rolesList.forEach(
        (role) =>
            select.append(
                $('<option>')
                    .val(role.id)
                    .text(role.name.startsWith('ROLE_') ? role.name.substring(5) : role.name)
            )
    );
})

function buildUserJson() {
    const user = {};

    simpleUserFields.forEach((field) => user[field] = $(`.add-form .form-control.${field}`).val());

    user['roles'] = $('.add-form option:selected').map(function () {
        return { id: parseInt($(this).val()) };
    }).get();

    return user;
}

async function fetchAndParseJson(url) {
    const response = await fetch(url);

    if (response.status < 400) {
        return response.json();

    } else {
        alert("HTTP error: " + response.status);
    }
}