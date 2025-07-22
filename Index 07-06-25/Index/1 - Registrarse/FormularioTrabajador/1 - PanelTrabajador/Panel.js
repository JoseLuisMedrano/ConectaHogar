document.addEventListener('DOMContentLoaded', function() {
  // Mostrar contenido inicial
  mostrarContenido('inicio');
  
  // Inicializar eventos de calificación
  document.addEventListener('click', function(e) {
    if (e.target.classList.contains('rating-star') || e.target.classList.contains('fa-star')) {
      const star = e.target.classList.contains('rating-star') ? e.target : e.target.parentElement;
      const ratingContainer = star.closest('.rating-stars') || star.closest('.rating');
      const stars = ratingContainer.querySelectorAll('.rating-star, .fa-star');
      const value = star.getAttribute('data-value') || star.parentElement.getAttribute('data-value');
      
      stars.forEach((s, index) => {
        const sValue = s.getAttribute('data-value') || s.parentElement.getAttribute('data-value');
        if (sValue <= value) {
          s.classList.add('active');
          if (s.classList.contains('fa-star')) {
            s.style.color = '#ffc107';
          }
        } else {
          s.classList.remove('active');
          if (s.classList.contains('fa-star')) {
            s.style.color = '#ddd';
          }
        }
      });
    }
  });
});

function mostrarContenido(opcion) {
  // Cambiar clase del body según la opción seleccionada
  document.body.className = ''; // Limpiar clases anteriores
  document.body.classList.add('theme-verde');
  
  const main = document.getElementById('main');
  
  let contenido = '';
  switch (opcion) {
    case 'inicio':
      contenido = `
        <div class="content-box">
          <h2><i class="fas fa-home"></i> Bienvenido al Panel del Trabajador</h2>
          <p>Selecciona una opción del menú para comenzar a gestionar tus trabajos.</p>
          
          <div class="trabajos-grid" style="margin-top: 2rem;">
            <div class="trabajo-card">
              <h3><i class="fas fa-briefcase"></i> Trabajos Disponibles</h3>
              <p>Revisa los nuevos trabajos disponibles y acepta los que desees realizar.</p>
              <button onclick="mostrarContenido('trabajos')" style="width: 100%; margin-top: 1rem;">
                <i class="fas fa-arrow-right"></i> Ver trabajos
              </button>
            </div>
            
            <div class="trabajo-card">
              <h3><i class="fas fa-history"></i> Historial</h3>
              <p>Revisa tu historial de trabajos realizados y tus calificaciones.</p>
              <button onclick="mostrarContenido('historial')" style="width: 100%; margin-top: 1rem;">
                <i class="fas fa-arrow-right"></i> Ver historial
              </button>
            </div>
            
            <div class="trabajo-card">
              <h3><i class="fas fa-user-cog"></i> Mi Perfil</h3>
              <p>Actualiza tus datos personales, habilidades y disponibilidad.</p>
              <button onclick="mostrarContenido('perfil')" style="width: 100%; margin-top: 1rem;">
                <i class="fas fa-edit"></i> Editar perfil
              </button>
            </div>
          </div>
        </div>
        
        <div class="content-box">
          <h2><i class="fas fa-bell"></i> Notificaciones Recientes</h2>
          <div class="mensaje mensaje-exito">
            <i class="fas fa-check-circle"></i>
            <div>
              <strong>Nuevo trabajo disponible</strong>
              <p>Tienes una nueva solicitud de servicio de jardinería en La Molina.</p>
              <small class="fecha">Hace 2 horas</small>
            </div>
          </div>
          
          <div class="mensaje">
            <i class="fas fa-info-circle"></i>
            <div>
              <strong>Recordatorio</strong>
              <p>No olvides completar la información de tu perfil para obtener más trabajos.</p>
              <small class="fecha">Ayer</small>
            </div>
          </div>
        </div>
      `;
      break;
      
    case 'trabajos':
      contenido = `
        <div class="content-box">
          <h2><i class="fas fa-briefcase"></i> Trabajos Disponibles</h2>
          <p>Aquí puedes ver los trabajos disponibles y aceptar los que desees realizar.</p>
          
          <div class="trabajos-grid" style="margin-top: 2rem;">
            <div class="trabajo-card">
              <h3><i class="fas fa-leaf"></i> Jardinería Residencial</h3>
              <p><strong>Cliente:</strong> Juan Pérez</p>
              <p><strong>Ubicación:</strong> La Molina, Lima</p>
              <p><strong>Presupuesto:</strong> S/ 150</p>
              <p class="fecha">Fecha solicitada: 15/06/2023</p>
              
              <div class="descripcion-trabajo">
                <strong>Descripción:</strong>
                <p>Necesito podar 2 árboles medianos y cortar el césped de un jardín de aproximadamente 100m².</p>
              </div>
              
              <div class="acciones-trabajo">
                <button class="btn-aceptar" onclick="aceptarTrabajo(1)"><i class="fas fa-check"></i> Aceptar</button>
                <button class="btn-rechazar" onclick="rechazarTrabajo(1)"><i class="fas fa-times"></i> Rechazar</button>
              </div>
            </div>
            
            <div class="trabajo-card">
              <h3><i class="fas fa-bolt"></i> Reparación Eléctrica</h3>
              <p><strong>Cliente:</strong> María Gómez</p>
              <p><strong>Ubicación:</strong> Surco, Lima</p>
              <p><strong>Presupuesto:</strong> S/ 250</p>
              <p class="fecha">Fecha solicitada: 16/06/2023</p>
              
              <div class="descripcion-trabajo">
                <strong>Descripción:</strong>
                <p>Cambio de tomacorrientes en sala y comedor (aproximadamente 6 unidades).</p>
              </div>
              
              <div class="acciones-trabajo">
                <button class="btn-aceptar" onclick="aceptarTrabajo(2)"><i class="fas fa-check"></i> Aceptar</button>
                <button class="btn-rechazar" onclick="rechazarTrabajo(2)"><i class="fas fa-times"></i> Rechazar</button>
              </div>
            </div>
          </div>
        </div>
      `;
      break;

    case 'historial':
      contenido = `
        <div class="content-box">
          <h2><i class="fas fa-history"></i> Historial de Trabajos</h2>
          
          <div class="trabajos-grid">
            <div class="trabajo-card">
              <h3><i class="fas fa-leaf"></i> Jardinería Residencial</h3>
              <p><strong>Cliente:</strong> Carlos Rodríguez</p>
              <p><strong>Ubicación:</strong> Miraflores, Lima</p>
              <p><strong>Pago recibido:</strong> S/ 180</p>
              <p class="fecha">Fecha: 10/06/2023</p>
              <p>Estado: <span class="estado estado-completado">Completado</span></p>
              
              <div class="rating-container">
                <div class="rating-title">Calificación recibida:</div>
                <div class="rating-stars">
                  <i class="fas fa-star active" style="color: #ffc107;"></i>
                  <i class="fas fa-star active" style="color: #ffc107;"></i>
                  <i class="fas fa-star active" style="color: #ffc107;"></i>
                  <i class="fas fa-star active" style="color: #ffc107;"></i>
                  <i class="fas fa-star" style="color: #ddd;"></i>
                </div>
                
                <div class="rating-feedback">
                  <strong>Comentario:</strong>
                  <p>"Excelente trabajo, muy puntual y profesional. Recomendado!"</p>
                </div>
              </div>
            </div>
            
            <div class="trabajo-card">
              <h3><i class="fas fa-tools"></i> Reparación de Grifo</h3>
              <p><strong>Cliente:</strong> Luisa Fernández</p>
              <p><strong>Ubicación:</strong> San Isidro, Lima</p>
              <p><strong>Pago recibido:</strong> S/ 120</p>
              <p class="fecha">Fecha: 05/06/2023</p>
              <p>Estado: <span class="estado estado-completado">Completado</span></p>
              
              <div class="rating-container">
                <div class="rating-title">Calificación recibida:</div>
                <div class="rating-stars">
                  <i class="fas fa-star active" style="color: #ffc107;"></i>
                  <i class="fas fa-star active" style="color: #ffc107;"></i>
                  <i class="fas fa-star active" style="color: #ffc107;"></i>
                  <i class="fas fa-star active" style="color: #ffc107;"></i>
                  <i class="fas fa-star active" style="color: #ffc107;"></i>
                </div>
                
                <div class="rating-feedback">
                  <strong>Comentario:</strong>
                  <p>"Resolvió el problema rápidamente y con materiales de calidad. Volveré a llamarlo!"</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      `;
      break;

case 'perfil':
  contenido = `
    <div class="content-box">
      <h2><i class="fas fa-user-edit"></i> Mi Perfil</h2>
      
      <div style="text-align: center; margin-bottom: 2rem;">
        <img src="https://via.placeholder.com/150" alt="Foto de perfil" class="profile-pic">
        <button style="margin-top: 0.5rem;"><i class="fas fa-camera"></i> Cambiar foto</button>
      </div>
      
      <form id="formPerfil">
        <div class="form-group">
          <label for="nombre"><i class="fas fa-user"></i> Nombre completo:</label>
          <input type="text" id="nombre" name="nombre" placeholder="Ingresa tu nombre completo" required>
        </div>

        <div class="form-group">
          <label for="email"><i class="fas fa-envelope"></i> Correo electrónico:</label>
          <input type="email" id="email" name="email" placeholder="Ingresa tu correo electrónico" required>
        </div>

        <div class="form-group">
          <label for="telefono"><i class="fas fa-phone"></i> Teléfono:</label>
          <input type="tel" id="telefono" name="telefono" placeholder="Ingresa tu número telefónico" required>
        </div>

        <div class="form-group">
          <label for="direccion"><i class="fas fa-home"></i> Dirección:</label>
          <textarea id="direccion" name="direccion" placeholder="Ingresa tu dirección" required></textarea>
        </div>

        <div class="form-group">
          <label><i class="fas fa-tools"></i> Especialidad:</label>
          <div class="especialidad-options">
            <label class="radio-option">
              <input type="radio" name="especialidad" value="gasfiteria" required> Gasfitería
            </label>
            <label class="radio-option">
              <input type="radio" name="especialidad" value="electricista"> Electricista
            </label>
            <label class="radio-option">
              <input type="radio" name="especialidad" value="jardineria"> Jardinería
            </label>
          </div>
        </div>

        <div class="form-group">
          <label for="descripcion"><i class="fas fa-align-left"></i> Descripción del perfil:</label>
          <textarea id="descripcion" name="descripcion" placeholder="Describe brevemente tu experiencia y servicios que ofreces" required></textarea>
        </div>

        <div class="form-group">
          <label for="experiencia"><i class="fas fa-briefcase"></i> Experiencia:</label>
          <textarea id="experiencia" name="experiencia" placeholder="Detalla tus años de experiencia y trabajos realizados" required></textarea>
        </div>

        <div class="form-group">
          <label><i class="fas fa-clock"></i> Disponibilidad:</label>
          <div class="disponibilidad-options">
            <label class="checkbox-option">
              <input type="checkbox"> Lunes a Viernes (8am - 6pm)
            </label>
            <label class="checkbox-option">
              <input type="checkbox"> Sábados (8am - 2pm)
            </label>
            <label class="checkbox-option">
              <input type="checkbox"> Domingos y feriados
            </label>
          </div>
        </div>

        <button type="submit" class="btn-guardar"><i class="fas fa-save"></i> Guardar Cambios</button>
      </form>
      <div id="mensajePerfil" class="mensaje-perfil"></div>
    </div>
  `;
      break;

    case 'notificaciones':
      contenido = `
        <div class="content-box">
          <h2><i class="fas fa-bell"></i> Notificaciones</h2>
          
          <div class="notificacion-item">
            <div class="mensaje mensaje-exito">
              <i class="fas fa-check-circle"></i>
              <div>
                <strong>Nuevo trabajo disponible</strong>
                <p>Tienes una nueva solicitud de servicio de jardinería en La Molina.</p>
                <small class="fecha">Hace 2 horas</small>
              </div>
            </div>
          </div>
          
          <div class="notificacion-item">
            <div class="mensaje">
              <i class="fas fa-info-circle"></i>
              <div>
                <strong>Recordatorio</strong>
                <p>No olvides completar la información de tu perfil para obtener más trabajos.</p>
                <small class="fecha">Ayer</small>
              </div>
            </div>
          </div>
          
          <div class="notificacion-item">
            <div class="mensaje">
              <i class="fas fa-star"></i>
              <div>
                <strong>Nueva calificación</strong>
                <p>Carlos Rodríguez te ha calificado con 4 estrellas por tu trabajo de jardinería.</p>
                <small class="fecha">3 días atrás</small>
              </div>
            </div>
          </div>
          
          <div class="notificacion-item">
            <div class="mensaje">
              <i class="fas fa-money-bill-wave"></i>
              <div>
                <strong>Pago recibido</strong>
                <p>Has recibido S/ 180 por tu trabajo en Miraflores.</p>
                <small class="fecha">5 días atrás</small>
              </div>
            </div>
          </div>
        </div>
      `;
      break;
  }
  
  main.innerHTML = contenido;
  
  // Configurar eventos para formularios
  if (opcion === 'perfil') {
    document.getElementById('formPerfil').addEventListener('submit', function(e) {
      e.preventDefault();
      document.getElementById('mensajePerfil').innerHTML = `
        <div class="mensaje mensaje-exito">
          <i class="fas fa-check-circle"></i>
          <div>
            <strong>Perfil actualizado</strong>
            <p>Tus datos se han guardado correctamente.</p>
          </div>
        </div>
      `;
    });
  }
}

function aceptarTrabajo(id) {
  const main = document.getElementById('main');
  main.innerHTML = `
    <div class="content-box">
      <h2><i class="fas fa-check-circle"></i> Trabajo Aceptado</h2>
      <div class="mensaje mensaje-exito">
        <i class="fas fa-check-circle"></i>
        <div>
          <strong>¡Has aceptado el trabajo!</strong>
          <p>El cliente ha sido notificado. Revisa los detalles del trabajo a continuación.</p>
        </div>
      </div>
      
      <div class="trabajo-card" style="margin-top: 2rem;">
        <h3><i class="fas fa-leaf"></i> Jardinería Residencial</h3>
        <p><strong>Cliente:</strong> Juan Pérez</p>
        <p><strong>Ubicación:</strong> La Molina, Lima</p>
        <p><strong>Presupuesto:</strong> S/ 150</p>
        <p class="fecha">Fecha solicitada: 15/06/2023</p>
        
        <div class="descripcion-trabajo">
          <strong>Descripción:</strong>
          <p>Necesito podar 2 árboles medianos y cortar el césped de un jardín de aproximadamente 100m².</p>
        </div>
        
        <div class="acciones-trabajo">
          <button class="btn-completar" onclick="completarTrabajo(${id})" style="width: 100%;">
            <i class="fas fa-check-double"></i> Marcar como completado
          </button>
        </div>
      </div>
      
      <div style="margin-top: 2rem;">
        <button onclick="mostrarContenido('trabajos')" style="width: 100%;">
          <i class="fas fa-arrow-left"></i> Volver a trabajos disponibles
        </button>
      </div>
    </div>
  `;
}

function rechazarTrabajo(id) {
  const main = document.getElementById('main');
  main.innerHTML = `
    <div class="content-box">
      <h2><i class="fas fa-times-circle"></i> Trabajo Rechazado</h2>
      <div class="mensaje">
        <i class="fas fa-info-circle"></i>
        <div>
          <strong>Has rechazado el trabajo</strong>
          <p>El cliente será notificado de tu decisión.</p>
        </div>
      </div>
      
      <div style="margin-top: 2rem;">
        <button onclick="mostrarContenido('trabajos')" style="width: 100%;">
          <i class="fas fa-arrow-left"></i> Volver a trabajos disponibles
        </button>
      </div>
    </div>
  `;
}

function completarTrabajo(id) {
  const main = document.getElementById('main');
  main.innerHTML = `
    <div class="content-box">
      <h2><i class="fas fa-check-double"></i> Trabajo Completado</h2>
      <div class="mensaje mensaje-exito">
        <i class="fas fa-check-circle"></i>
        <div>
          <strong>¡Trabajo marcado como completado!</strong>
          <p>El cliente será notificado y podrá calificar tu servicio.</p>
        </div>
      </div>
      
      <div style="margin-top: 2rem;">
        <button onclick="mostrarContenido('historial')" style="width: 100%;">
          <i class="fas fa-history"></i> Ver mi historial de trabajos
        </button>
      </div>
    </div>
  `;
}

function cambiarTema(tema) {
  document.body.className = '';
  document.body.classList.add(`theme-${tema}`);
  
  // Guardar preferencia de tema en localStorage
  localStorage.setItem('temaPreferido', tema);
}

// Cargar tema preferido al cargar la página
window.addEventListener('load', function() {
  const temaGuardado = localStorage.getItem('temaPreferido') || 'verde';
  cambiarTema(temaGuardado);
});