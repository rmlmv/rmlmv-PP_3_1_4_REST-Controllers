const findMeUrl = '/api/users/me'
const simpleUserFields = ['id', 'firstName', 'lastName', 'age', 'email'];

$(async () => {
    let user = await getUserInfo(findMeUrl);
    makeUserTable(user);
})

async function getUserInfo(url) {
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
