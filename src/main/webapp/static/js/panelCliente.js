/**
 * Script para manejar la navegación por pestañas en el panel del cliente.
 */
document.addEventListener('DOMContentLoaded', function () {
    // Selecciona todos los enlaces de la barra lateral que tienen el atributo 'data-section'
    const navLinks = document.querySelectorAll('.sidebar .nav-link[data-section]');
    // Selecciona todos los contenedores de contenido
    const contentSections = document.querySelectorAll('.content-section');

    // Función para mostrar la sección correcta
    function showSection(sectionId) {
        contentSections.forEach(section => {
            if (section.id === sectionId) {
                section.classList.remove('d-none'); // Muestra la sección objetivo
            } else {
                section.classList.add('d-none'); // Oculta las demás
            }
        });
    }

    // Añade un evento de clic a cada enlace de navegación
    navLinks.forEach(link => {
        link.addEventListener('click', function (event) {
            event.preventDefault(); // Evita que el enlace recargue la página

            // Marca el enlace actual como activo
            navLinks.forEach(nav => nav.classList.remove('active'));
            this.classList.add('active');

            // Obtiene el ID de la sección a mostrar desde el atributo 'data-section'
            const targetSectionId = this.dataset.section + '-section';
            showSection(targetSectionId);
        });
    });

    // Por defecto, al cargar la página, muestra la primera sección ("Mi Perfil")
    // y asegúrate de que su enlace esté activo.
    const initialActiveLink = document.querySelector('.sidebar .nav-link.active');
    if (initialActiveLink) {
        const initialSectionId = initialActiveLink.dataset.section + '-section';
        showSection(initialSectionId);
    }
});