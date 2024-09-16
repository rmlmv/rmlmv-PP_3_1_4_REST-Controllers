let delModal;

$(() => {
    delModal = new bootstrap.Modal(document.getElementById('del-user-modal'))

    $(document).on('userTableLoaded', () => {
        $('.delete-btn').each(function () {
            $(this).click(function () {
                transferUserDataToModal($(this), 'del-form');
            });
        });
    });

    $('#del-btn').on('click', async  () => {

        const userToDeleteId = $('.del-form .form-control.id').val();

        const response = await fetch(`${apiUrl}/${userToDeleteId}`, { method: "DELETE" });

        if (response.status === 204) {
            $(`#${userToDeleteId}`).remove();
        }

        delModal.hide();
    });
})