document.addEventListener('DOMContentLoaded', function () {
    const navLinks = document.querySelectorAll('.sidebar .nav-link');
    const contentSections = document.querySelectorAll('.content-section');

    // Función para mostrar la sección correcta y resaltar el enlace del menú
    function showSection(sectionId) {
        // 1. Ocultar todas las secciones de contenido
        contentSections.forEach(section => {
            section.classList.remove('active');
        });

        // 2. Mostrar la sección seleccionada
        const targetSection = document.getElementById(sectionId + '-section');
        if (targetSection) {
            targetSection.classList.add('active');
        }

        // 3. Resaltar el enlace del menú correspondiente
        navLinks.forEach(link => {
            link.classList.remove('active-nav');
            if (link.dataset.section === sectionId) {
                link.classList.add('active-nav');
            }
        });
    }

    // Añadir el evento 'click' a cada enlace del menú
    navLinks.forEach(link => {
        // Asegurarse de que el enlace tenga el atributo data-section
        if (link.dataset.section) {
            link.addEventListener('click', function (event) {
                event.preventDefault(); // Evita que el enlace recargue la página
                const sectionId = this.dataset.section;
                showSection(sectionId);
            });
        }
    });

    // Mostrar la sección de "Inicio" por defecto al cargar la página
    showSection('inicio');
});