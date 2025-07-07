// Archivo: /static/js/panelTecnico.js

document.addEventListener('DOMContentLoaded', function () {

    // --- LÓGICA DE NAVEGACIÓN PRINCIPAL (SIDEBAR) ---
    const navLinks = document.querySelectorAll('.sidebar .nav-link[data-section]');
    const contentSections = document.querySelectorAll('.content-section');

    // Función que muestra una sección y oculta las demás
    function showSection(sectionId) {
        contentSections.forEach(section => {
            if (section.id === sectionId) {
                section.classList.remove('d-none');
            } else {
                section.classList.add('d-none');
            }
        });
    }

    // Asigna el evento de clic a cada enlace de la barra lateral
    navLinks.forEach(link => {
        link.addEventListener('click', function (event) {
            event.preventDefault(); // Previene la recarga de la página

            // Actualiza cuál enlace se ve como 'activo'
            navLinks.forEach(nav => nav.classList.remove('active'));
            this.classList.add('active');

            // Muestra la sección correspondiente
            const targetSectionId = this.dataset.section + '-section';
            showSection(targetSectionId);
        });
    });

    // --- LÓGICA PARA EDITAR/VISUALIZAR EL PERFIL ---
    const btnEditar = document.getElementById('btn-editar-perfil');
    const btnCancelar = document.getElementById('btn-cancelar-edicion');
    const displayDiv = document.getElementById('perfil-display');
    const editFormDiv = document.getElementById('perfil-edit-form');

    // Se asegura de que los botones y divs existan antes de añadirles eventos
    if (btnEditar && btnCancelar && displayDiv && editFormDiv) {

        // Evento para el botón "Editar Perfil"
        btnEditar.addEventListener('click', () => {
            displayDiv.classList.add('d-none');    // Oculta la vista de datos
            editFormDiv.classList.remove('d-none'); // Muestra el formulario de edición
        });

        // Evento para el botón "Cancelar" en el formulario
        btnCancelar.addEventListener('click', () => {
            editFormDiv.classList.add('d-none');    // Oculta el formulario
            displayDiv.classList.remove('d-none');  // Vuelve a mostrar los datos
        });
    }
});