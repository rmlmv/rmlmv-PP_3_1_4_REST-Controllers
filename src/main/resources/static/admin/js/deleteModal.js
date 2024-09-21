let delModal;

$(() => {
    delModal = new bootstrap.Modal(document.getElementById('del-user-modal'))

    $(document).on('usersTableLoaded', () => {
        $('.delete-btn').each(function () {
            $(this).click(function () {
                transferUserDataToModal($(this), 'del-form');
            });
        });
    });

    $(document).on('rolesListLoaded', () => {
        const select = $('.del-form .form-select.roles');
        rolesList.forEach(
            (role) =>
                select.append(
                    $('<option>')
                        .val(role.id)
                        .text(role.name.startsWith('ROLE_') ? role.name.substring(5) : role.name)
                )
        );
    });

    $('#del-btn').on('click', async  () => {

        const userToDeleteId = $('.del-form .form-control.id').val();

        const response = await fetch(`${usersApiUrl}/${userToDeleteId}`, { method: "DELETE" });

        if (response.status === 204) {
            $(`#${userToDeleteId}`).remove();
        }

        delModal.hide();
    });
})