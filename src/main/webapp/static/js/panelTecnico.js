document.addEventListener('DOMContentLoaded', function () {
    const navLinks = document.querySelectorAll('.sidebar .nav-link');
    const contentSections = document.querySelectorAll('.content-section');

    function showSection(sectionId) {
        // Oculta todas las secciones
        contentSections.forEach(section => {
            section.classList.remove('active');
        });

        // Muestra la sección deseada
        const targetSection = document.getElementById(sectionId + '-section');
        if (targetSection) {
            targetSection.classList.add('active');
        }

        // Resalta el enlace del menú correspondiente
        navLinks.forEach(link => {
            link.classList.remove('active-nav');
            if (link.dataset.section === sectionId) {
                link.classList.add('active-nav');
            }
        });
    }

    // Añadir el evento 'click' a cada enlace del menú
    navLinks.forEach(link => {
        if (link.dataset.section) {
            link.addEventListener('click', function (event) {
                event.preventDefault();
                const sectionId = this.dataset.section;
                showSection(sectionId);
            });
        }
    });

    // Mostrar la sección de "Inicio" por defecto al cargar la página
    showSection('inicio');
});