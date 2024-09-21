const findMeUrl = '/api/users/me'
const simpleUserFields = ['id', 'firstName', 'lastName', 'age', 'email'];

$(async () => {
    const mePromise = fetchAndParseJson(findMeUrl);
    mePromise.then(me => {
        $('#top-bar .user-email').text(me.email);
        const rolesSpan = $('#top-bar .user-roles');
        me.roles.forEach(role => rolesSpan.append(
            ' ' + (role.name.startsWith('ROLE_') ? role.name.substring(5) : role.name)
        ));

        if (me.roles.some(role => role.name === 'ROLE_ADMIN')) {
            $('#side-bar ul').prepend('<li class="nav-item">' +
                '<a href="/admin/admin.html" class="nav-link link-bodyemphasis">Admin</a></li>');
        }

        makeUserTable(me);
    });
})

async function fetchAndParseJson(url) {
    let response = await fetch(url);

    if (response.status < 400) {
        return response.json();

    } else {
        alert("HTTP error: " + response.status);
    }
}

async function makeUserTable(user) {
    const tbody = $('#tbody');

    const row = $('<tr>');

    simpleUserFields.forEach(field => {
        row.append($('<td>').text(user[field]))

    })

    row.append($('<td>').text(
        user['roles'].map(role => role.name.startsWith('ROLE_') ? role.name.substring(5) : role.name).join(' ')
    ));

    tbody.append(row);
}
