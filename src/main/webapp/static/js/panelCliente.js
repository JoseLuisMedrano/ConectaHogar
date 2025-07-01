document.addEventListener('DOMContentLoaded', function () {
    console.log("Script cargado. Iniciando JavaScript para panelCliente.");

    // Objeto con los tipos de servicio para cada especialista
    const tiposDeServicio = {
        electricista: [
            "Instalación eléctrica nueva",
            "Reparación de cortocircuitos",
            "Mantenimiento de cableado",
            "Instalación de luminarias",
            "Revisión de tableros eléctricos"
        ],
        gasfitero: [
            "Fugas y filtraciones",
            "Instalación de tuberías",
            "Reparación de inodoros",
            "Desatoros",
            "Mantenimiento de grifería"
        ],
        jardinero: [
            "Consulta y asesoría",
            "Mantenimiento de plantas",
            "Diseño y paisajismo",
            "Poda de árboles",
            "Instalación de riego"
        ]
    };

    // Función para actualizar las opciones del segundo select
    function actualizarTipoServicio() {
        const especialistaSelect = document.getElementById('tipoEspecialista');
        const servicioEspecificoSelect = document.getElementById('tipoServicioEspecifico');
        const selectedEspecialista = especialistaSelect.value;
        console.log("Actualizando tipo de servicio. Especialista seleccionado:", selectedEspecialista);

        servicioEspecificoSelect.innerHTML = '<option value="">Seleccione un tipo de servicio</option>';

        if (selectedEspecialista) {
            const servicios = tiposDeServicio[selectedEspecialista];
            if (servicios) {
                servicios.forEach(servicio => {
                    const option = document.createElement('option');
                    option.value = servicio;
                    option.textContent = servicio;
                    servicioEspecificoSelect.appendChild(option);
                });
                console.log("Servicios cargados para " + selectedEspecialista + ":", servicios);
            }
        }
    }

    // Event listener para el cambio en el select de tipoEspecialista
    const especialistaSelect = document.getElementById('tipoEspecialista');
    if (especialistaSelect) {
        especialistaSelect.addEventListener('change', actualizarTipoServicio);
    }

    const navLinks = document.querySelectorAll('.navbar-nav .nav-link');
    const contentSections = document.querySelectorAll('.content-section');
    // Si 'initialSection' viene del servidor, úsala, de lo contrario, muestra 'inicio' por defecto
    const initialSectionId = document.body.dataset.initialSection || "inicio"; // Usar un data-attribute en el body o directamente en la lógica del servlet

    console.log("Enlaces de navegación encontrados:", navLinks.length);
    console.log("Secciones de contenido encontradas:", contentSections.length);

    function showSection(sectionId) {
        console.log("Intentando mostrar sección:", sectionId);

        // Oculta todas las secciones
        contentSections.forEach(section => {
            section.classList.remove('active');
            section.style.display = 'none'; // Asegura que estén ocultas
        });

        // Muestra la sección deseada
        const targetSection = document.getElementById(sectionId + '-section');
        if (targetSection) {
            targetSection.classList.add('active');
            targetSection.style.display = 'block'; // Asegura que se muestre
            console.log("Sección '" + sectionId + "' activada.");
            if (sectionId === 'crearSolicitud') {
                actualizarTipoServicio(); // Llama a esta función solo cuando la sección de crear solicitud está activa
            }
        } else {
            console.warn("Sección '" + sectionId + "-section' no encontrada. Mostrando 'inicio'.");
            document.getElementById('inicio-section').classList.add('active');
            document.getElementById('inicio-section').style.display = 'block';
            document.getElementById('nav-inicio').classList.add('active-nav');
        }

        // Remueve la clase 'active-nav' de todos los enlaces y la añade al enlace de la sección activa
        navLinks.forEach(link => {
            link.classList.remove('active-nav');
            if (link.dataset.section === sectionId) {
                link.classList.add('active-nav');
                console.log("Clase 'active-nav' aplicada a:", link.id);
            }
        });
    }

    navLinks.forEach(link => {
        if (link.dataset.section) {
            console.log("Añadiendo evento click a enlace:", link.dataset.section);
            link.addEventListener('click', function (event) {
                event.preventDefault(); // Evita el comportamiento por defecto del enlace
                console.log("Clic en enlace de navegación. data-section:", this.dataset.section);
                showSection(this.dataset.section);
            });
        }
    });

    // Mostrar la sección inicial al cargar la página
    showSection(initialSectionId);

    // Lógica para el manejo de formularios y mensajes de éxito/error (si aún la necesitas aquí)
    // Nota: El contenido dinámico de "rating-star" y "formServicio/formPerfil" 
    // parece estar en el snippet de JS que tenías, pero no en tu JSP principal actual.
    // Si necesitas esa funcionalidad, asegúrate de que los elementos con esos IDs/clases
    // existan en las secciones que se cargan.
    
    // Ejemplo de cómo manejar submits, si no van a un servlet directamente
    document.querySelector('main').addEventListener('submit', (e) => {
        // Esta parte es más compleja si la submit realmente envía datos a un servlet.
        // Si es solo para mostrar un mensaje de éxito sin recargar, puedes mantenerla.
        // Si el formulario debe hacer un POST al servlet, esta lógica de JS no es necesaria aquí.
        // La puse como comentario porque tu formulario ya tiene action="/crearSolicitudServlet"
        // e.preventDefault(); 
        // const form = e.target;
        // ... (lógica de mensaje de éxito/error de formularios si se maneja 100% en el cliente)
    });
});