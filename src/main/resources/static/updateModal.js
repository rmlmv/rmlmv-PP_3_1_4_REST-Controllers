let updModal;

$(() => {
        updModal = new bootstrap.Modal(document.getElementById('upd-user-modal'))

        $(document).on('userTableLoaded', () => {
            $('.edit-btn').each(function() {
                $(this).click(function() {
                    $('.upd-form .alert-danger').hide();

                    transferUserDataToModal($(this), 'upd-form');
                });
            });
        });

        $('#upd-btn').on('click', async () => {
            $('.upd-form .alert-danger').hide();

            const userToUpdate = buildUserJson();

            const response = await fetch(apiUrl, {
                method: "PUT",
                body: JSON.stringify(userToUpdate),
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                updateUserInTable((await response.json()).userDto);

                updModal.hide();

            } else {
                const validationErrors = (await response.json()).validationErrors;
                if (validationErrors != null) {
                    Object.entries(validationErrors).forEach(([field, message]) => {
                        $(`.upd-form .alert-danger.${field}`).text(message).show();
                    });
                }
            }
        });
    }
);

function buildUserJson() {
    const user = {};

    simpleUserFields.forEach((field) => user[field] = $(`.upd-form .form-control.${field}`).val());

    user['roles'] = $('.upd-form option:selected').map(function () {
        return { id: parseInt($(this).val()) };
    }).get();

    return user;
}

function updateUserInTable(updatedUser) {
    const row = $(`#${updatedUser.id}`);

    simpleUserFields.forEach(field => {
        row.find(`.${field}`).text(updatedUser[field]);
    });

    row.find('.roles').text(
        updatedUser.roles.map(
            role => role.name.startsWith('ROLE_')
                ? role.name.substring(5)
                : role.name
        ).join(' ')
    );
}