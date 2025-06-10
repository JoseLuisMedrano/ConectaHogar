// Esperar a que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', function () {
    const navbar = document.querySelector('.navbar');

    // Función para cambiar la clase del navbar cuando se hace scroll
    function toggleNavbarShrink() {
        if (window.scrollY > 50) {
            navbar.classList.add('navbar-shrink');
        } else {
            navbar.classList.remove('navbar-shrink');
        }
    }

    // Ejecutar al cargar la página y cuando se hace scroll
    toggleNavbarShrink();
    window.addEventListener('scroll', toggleNavbarShrink);

    // Modal de selección de rol
    const roleOptions = document.querySelectorAll('.role-option');
    let selectedRoleInput = document.querySelector('#selectedRole'); // input oculto para guardar el rol

    roleOptions.forEach(option => {
        option.addEventListener('click', function () {
            // Quitar clase seleccionada de otros
            roleOptions.forEach(o => o.classList.remove('selected'));

            // Agregar clase al actual
            this.classList.add('selected');

            // Guardar el valor del rol
            const role = this.getAttribute('data-role');
            if (selectedRoleInput) {
                selectedRoleInput.value = role;
            }

            // También puedes mostrar un mensaje, cambiar estilos, etc.
            console.log('Rol seleccionado:', role);
        });
    });

    // Opcional: Validar que se haya seleccionado un rol antes de continuar
    const confirmButton = document.querySelector('#confirmRole');
    if (confirmButton) {
        confirmButton.addEventListener('click', function () {
            if (!selectedRoleInput.value) {
                alert('Por favor, selecciona un rol para continuar.');
            } else {
                // Redirige o cierra modal, etc.
                console.log('Rol confirmado:', selectedRoleInput.value);
                // Ejemplo: cerrar el modal
                const modal = bootstrap.Modal.getInstance(document.getElementById('roleModal'));
                modal.hide();
            }
        });
    }
});
