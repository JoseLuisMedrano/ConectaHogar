document.addEventListener('DOMContentLoaded', function() {
    console.log("Script cargado. Iniciando JavaScript para panelTecnico.");

    // Selecciona todos los enlaces de navegación con la clase 'nav-link' que están dentro de '.navbar-nav'
    const navLinks = document.querySelectorAll('.navbar-nav .nav-link'); 
    // Selecciona todas las secciones de contenido con la clase 'content-section'
    const contentSections = document.querySelectorAll('.content-section'); 

    // Define la sección inicial por defecto al cargar la página
    const initialSectionId = "inicio"; 

    console.log("Enlaces de navegación encontrados:", navLinks.length);
    console.log("Secciones de contenido encontradas:", contentSections.length);

    function showSection(sectionId) {
        console.log("Intentando mostrar sección:", sectionId);

        // Oculta todas las secciones de contenido
        contentSections.forEach(section => {
            section.classList.remove('active');
            section.style.display = 'none'; // Asegura que estén ocultas si el CSS no lo maneja completamente
        });

        // Muestra la sección de destino añadiendo la clase 'active'
        const targetSection = document.getElementById(sectionId + '-section');
        if (targetSection) {
            targetSection.classList.add('active');
            targetSection.style.display = 'block'; // Asegura que se muestre
            console.log("Sección '" + sectionId + "' activada.");
        } else {
            console.warn("Sección '" + sectionId + "-section' no encontrada. Asegúrate de que el ID en el HTML sea correcto.");
            // Si la sección no se encuentra, vuelve a la sección de inicio como fallback
            document.getElementById('inicio-section').classList.add('active');
            document.getElementById('inicio-section').style.display = 'block';
            document.getElementById('nav-inicio').classList.add('active-nav');
        }

        // Desactiva visualmente todos los enlaces de navegación
        navLinks.forEach(link => {
            link.classList.remove('active-nav');
        });

        // Activa visualmente el enlace de navegación actual
        // Es importante que los IDs de los enlaces sean 'nav-inicio', 'nav-trabajos', etc.
        const currentNavLink = document.getElementById('nav-' + sectionId);
        if (currentNavLink) {
            currentNavLink.classList.add('active-nav');
            console.log("Clase 'active-nav' aplicada a:", currentNavLink.id);
        }
    }

    // Añade un event listener a cada enlace de navegación
    navLinks.forEach(link => {
        // Excluir el botón de cerrar sesión si tiene la clase 'nav-link-button'
        // para que no intente mostrar una sección de contenido (ya que es un submit de formulario)
        if (!link.classList.contains('nav-link-button')) {
            console.log("Añadiendo evento click a enlace:", link.dataset.section);
            link.addEventListener('click', function(e) {
                e.preventDefault(); // Evita el comportamiento predeterminado del enlace (navegar a #)
                const sectionId = this.dataset.section; // Obtiene el ID de la sección del atributo data-section
                console.log("Clic en enlace de navegación. data-section:", sectionId);
                showSection(sectionId); // Llama a la función para mostrar la sección
            });
        }
    });

    // Mostrar la sección 'inicio' por defecto al cargar la página
    showSection(initialSectionId);
});