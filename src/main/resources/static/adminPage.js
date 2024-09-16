const apiUrl = '/api/users';
const simpleUserFields = ['id', 'firstName', 'lastName', 'age', 'email', 'password'];
let userTableLoadedEvent;

$(async () => {
        userTableLoadedEvent = new jQuery.Event('userTableLoaded')

        let users = await findAllWithRoles(apiUrl);

        makeUsersTable(users);

        $(document).trigger(userTableLoadedEvent);
    }
);

async function findAllWithRoles(url) {
    const response = await fetch(url);

    if (response.status < 400) {
        return response.json();

    } else {
        alert("HTTP error: " + response.status);
    }
}

function makeUsersTable(users) {
    const tbody = $('#tbody');

    users.forEach(user => {
        const row = $('<tr>').attr('id', `${user.id}`);

        simpleUserFields.forEach(field =>
            row.append(
                $('<td>')
                    .addClass(field)
                    .text(user[field])
                    .each(function () {             // Метода с более подходящим названием не нашел)
                        if (field === 'password') {
                            $(this).hide();
                        }
                    })
            )
        )

        row.append(
            $('<td>')
                .addClass('roles')
                .text(
                    user['roles']
                        .map(role => role.name.startsWith('ROLE_') ? role.name.substring(5) : role.name)
                        .join(' ')
                )
        )

        row.append(
            $('<td>').append(
                $('<button>')
                    .text('Edit')
                    .addClass('btn btn-primary btn-sm edit-btn')
                    .attr('type', 'button')
                    .attr('data-bs-toggle', 'modal')
                    .attr('data-bs-target', '#upd-user-modal')
            )
        );

        row.append(
            $('<td>').append(
                $('<button>')
                    .text('Delete')
                    .addClass('btn btn-danger btn-sm delete-btn')
                    .attr('type', 'button')
                    .attr('data-bs-toggle', 'modal')
                    .attr('data-bs-target', '#del-user-modal')
            )
        );

        tbody.append(row);
    });
}

function transferUserDataToModal(button, formClass) {
    const row = button.closest('tr');

    simpleUserFields.forEach(field =>
        $(`.${formClass} .form-control.${field}`).val(row.find(`.${field}`).text())
    );

    const roles = row.find('.roles').text().split(' ');
    $(`.${formClass} option`).each(function () {
        $(this).prop('selected', roles.includes($(this).text()));
    });
}
