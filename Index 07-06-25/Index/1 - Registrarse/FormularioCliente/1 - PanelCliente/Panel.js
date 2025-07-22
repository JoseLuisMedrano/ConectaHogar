// --- JS Mejorado ---
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
  if(opcion === 'servicio') document.body.classList.add('theme-verde');
  else if(opcion === 'calificar') document.body.classList.add('theme-azul');
  else if(opcion === 'oscuro') document.body.classList.add('theme-oscuro');
  
  const main = document.getElementById('main');
  
  let contenido = '';
  switch (opcion) {
    case 'inicio':
      contenido = `
        <div class="content-box">
          <h2><i class="fas fa-home"></i> Bienvenido al Panel del Cliente</h2>
          <p>Selecciona una opción del menú para comenzar a gestionar tus servicios.</p>
          
          <div class="servicios-grid" style="margin-top: 2rem;">
            <div class="servicio-card">
              <h3><i class="fas fa-tools"></i> Servicio Rápido</h3>
              <p>Solicita un técnico especializado para resolver tus problemas en el hogar.</p>
              <button onclick="mostrarContenido('servicio')" style="width: 100%; margin-top: 1rem;">
                <i class="fas fa-plus"></i> Solicitar ahora
              </button>
            </div>
            
            <div class="servicio-card">
              <h3><i class="fas fa-star"></i> Calificaciones</h3>
              <p>Evalúa a los técnicos que te han atendido y ayuda a mejorar nuestro servicio.</p>
              <button onclick="mostrarContenido('historial')" style="width: 100%; margin-top: 1rem;">
                <i class="fas fa-arrow-right"></i> Ver servicios
              </button>
            </div>
            
            <div class="servicio-card">
              <h3><i class="fas fa-user-cog"></i> Mi Cuenta</h3>
              <p>Actualiza tus datos personales, dirección y preferencias de contacto.</p>
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
              <strong>Servicio programado</strong>
              <p>Tu solicitud de jardinería ha sido confirmada para el 15 de junio.</p>
            </div>
          </div>
          
          <div class="mensaje">
            <i class="fas fa-info-circle"></i>
            <div>
              <strong>Recordatorio</strong>
              <p>No olvides calificar al técnico que te atendió la semana pasada.</p>
            </div>
          </div>
        </div>
      `;
      break;
      
    case 'servicio':
      contenido = `
        <div class="content-box">
          <h2><i class="fas fa-tools"></i> Solicitar Servicio</h2>
          <form id="formServicio">
            <div class="form-group">
              <label for="tipo"><i class="fas fa-list-ul"></i> Tipo de servicio:</label>
              <select id="tipo" name="tipo" required>
                <option value="">Seleccione un servicio...</option>
                <option value="jardinería">🌿 Jardinería</option>
                <option value="electricidad">⚡ Electricidad</option>
                <option value="gasfitería">🚿 Gasfitería</option>
              </select>
            </div>

            <div class="form-group">
              <label for="descripcion"><i class="fas fa-align-left"></i> Descripción detallada:</label>
              <textarea id="descripcion" name="descripcion" placeholder="Describe el problema o servicio que necesitas..." required></textarea>
            </div>

            <div class="form-group">
              <label for="fecha"><i class="fas fa-calendar-alt"></i> Fecha deseada:</label>
              <input type="date" id="fecha" name="fecha" required>
            </div>

            <div class="form-group">
              <label for="hora"><i class="fas fa-clock"></i> Hora preferida:</label>
              <select id="hora" name="hora" required>
                <option value="">Seleccione una hora...</option>
                <option value="08:00-10:00">08:00 - 10:00</option>
                <option value="10:00-12:00">10:00 - 12:00</option>
                <option value="12:00-14:00">12:00 - 14:00</option>
                <option value="14:00-16:00">14:00 - 16:00</option>
                <option value="16:00-18:00">16:00 - 18:00</option>
              </select>
            </div>

            <div class="form-group">
              <label for="urgencia"><i class="fas fa-exclamation-triangle"></i> Nivel de urgencia:</label>
              <select id="urgencia" name="urgencia">
                <option value="normal">Normal (1-3 días)</option>
                <option value="urgente">Urgente (24 horas)</option>
                <option value="emergencia">Emergencia (mismo día)</option>
              </select>
            </div>

            <button type="submit"><i class="fas fa-paper-plane"></i> Enviar Solicitud</button>
          </form>
          <div id="mensajeConfirmacion" style="margin-top: 1.5rem;"></div>
        </div>
      `;
      break;

    case 'historial':
      contenido = `
        <div class="content-box">
          <h2><i class="fas fa-history"></i> Historial de Servicios</h2>
          
          <div class="servicios-grid">
            <div class="servicio-card">
              <h3><i class="fas fa-bolt"></i> Reparación eléctrica</h3>
              <p><strong>Técnico:</strong> Juan Pérez</p>
              <p><strong>Descripción:</strong> Cambio de tomacorrientes en sala</p>
              <p class="fecha">Fecha: 10/06/2023</p>
              <p>Estado: <span class="estado estado-completado">Completado</span></p>
              
              <!-- Sistema de calificación mejorado -->
              <div class="rating-container">
                <div class="rating-title">Calificar este servicio:</div>
                <div class="rating-stars">
                  <div class="rating-star" data-value="1"><i class="fas fa-star"></i></div>
                  <div class="rating-star" data-value="2"><i class="fas fa-star"></i></div>
                  <div class="rating-star" data-value="3"><i class="fas fa-star"></i></div>
                  <div class="rating-star" data-value="4"><i class="fas fa-star"></i></div>
                  <div class="rating-star" data-value="5"><i class="fas fa-star"></i></div>
                </div>
                <div class="rating-labels">
                  <span>Malo</span>
                  <span>Excelente</span>
                </div>
                
                <div class="rating-feedback">
                  <label for="comentario1"><i class="fas fa-comment"></i> Comentario (opcional):</label>
                  <textarea id="comentario1" class="rating-comment" placeholder="¿Cómo fue tu experiencia con este servicio?"></textarea>
                </div>
                
                <button class="rating-submit"><i class="fas fa-check"></i> Enviar Calificación</button>
              </div>
            </div>
            
            <div class="servicio-card">
              <h3><i class="fas fa-leaf"></i> Mantenimiento de jardín</h3>
              <p><strong>Técnico:</strong> María González</p>
              <p><strong>Descripción:</strong> Podado de árboles y césped</p>
              <p class="fecha">Fecha: 05/06/2023</p>
              <p>Estado: <span class="estado estado-completado">Completado</span></p>
              
              <div class="rating-container">
                <div class="rating-title">Calificar este servicio:</div>
                <div class="rating-stars">
                  <div class="rating-star" data-value="1"><i class="fas fa-star"></i></div>
                  <div class="rating-star" data-value="2"><i class="fas fa-star"></i></div>
                  <div class="rating-star" data-value="3"><i class="fas fa-star"></i></div>
                  <div class="rating-star" data-value="4"><i class="fas fa-star"></i></div>
                  <div class="rating-star" data-value="5"><i class="fas fa-star"></i></div>
                </div>
                <div class="rating-labels">
                  <span>Malo</span>
                  <span>Excelente</span>
                </div>
                
                <div class="rating-feedback">
                  <label for="comentario2"><i class="fas fa-comment"></i> Comentario (opcional):</label>
                  <textarea id="comentario2" class="rating-comment" placeholder="¿Cómo fue tu experiencia con este servicio?"></textarea>
                </div>
                
                <button class="rating-submit"><i class="fas fa-check"></i> Enviar Calificación</button>
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
          <textarea id="direccion" name="direccion" placeholder="Ingresa tu dirección completa" required></textarea>
          <div class="distrito-badge">Distrito: <span id="distrito-text">No especificado</span></div>
        </div>

        <div class="form-group">
          <label><i class="fas fa-bell"></i> Preferencias de notificación:</label>
          <div class="notificacion-options">
            <label class="checkbox-option">
              <input type="checkbox" checked> Correo electrónico
            </label>
            <label class="checkbox-option">
              <input type="checkbox" checked> Mensajes de texto
            </label>
          </div>
        </div>

        <button type="submit" class="btn-guardar"><i class="fas fa-save"></i> Guardar Cambios</button>
      </form>
      <div id="mensajePerfil" class="mensaje-perfil"></div>
    </div>
  `;
  break;

    case 'problema':
      contenido = `
        <div class="content-box">
          <h2><i class="fas fa-exclamation-circle"></i> Reportar Problema</h2>
          <form id="formProblema">
            <div class="form-group">
              <label for="tipoProblema"><i class="fas fa-list-ul"></i> Tipo de problema:</label>
              <select id="tipoProblema" name="tipoProblema" required>
                <option value="">Seleccione un tipo...</option>
                <option value="tecnico">Problema con técnico</option>
                <option value="facturacion">Problema de facturación</option>
                <option value="plataforma">Problema con la plataforma</option>
                <option value="otro">Otro problema</option>
              </select>
            </div>

            <div class="form-group">
              <label for="servicioRelacionado"><i class="fas fa-tools"></i> Servicio relacionado (opcional):</label>
              <select id="servicioRelacionado" name="servicioRelacionado">
                <option value="">No aplica</option>
                <option value="jardinería-10jun">Jardinería - 10/06/2023</option>
                <option value="electricidad-5jun">Electricidad - 05/06/2023</option>
                <option value="gasfiteria-28may">Gasfitería - 28/05/2023</option>
              </select>
            </div>

            <div class="form-group">
              <label for="descripcionProblema"><i class="fas fa-align-left"></i> Descripción detallada:</label>
              <textarea id="descripcionProblema" name="descripcionProblema" placeholder="Describe el problema con el mayor detalle posible..." required></textarea>
            </div>

            <div class="form-group">
              <label for="archivos"><i class="fas fa-paperclip"></i> Adjuntar archivos (opcional):</label>
              <input type="file" id="archivos" name="archivos" multiple>
              <p style="font-size: 0.8rem; color: #666;">Puedes adjuntar fotos o documentos relacionados (máx. 5MB)</p>
            </div>

            <button type="submit"><i class="fas fa-paper-plane"></i> Enviar Reporte</button>
          </form>
          <div id="mensajeProblema" style="margin-top: 1.5rem;"></div>
        </div>
      `;
      break;
  }
  
  main.innerHTML = contenido;
  
  // Configurar eventos para formularios
  if (opcion === 'servicio') {
    document.getElementById('formServicio').addEventListener('submit', function(e) {
      e.preventDefault();
      document.getElementById('mensajeConfirmacion').innerHTML = `
        <div class="mensaje mensaje-exito">
          <i class="fas fa-check-circle"></i>
          <div>
            <strong>Solicitud enviada con éxito</strong>
            <p>Hemos recibido tu solicitud de servicio. Nos contactaremos contigo pronto para confirmar los detalles.</p>
          </div>
        </div>
      `;
      this.reset();
    });
  }
  
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
  
  if (opcion === 'problema') {
    document.getElementById('formProblema').addEventListener('submit', function(e) {
      e.preventDefault();
      document.getElementById('mensajeProblema').innerHTML = `
        <div class="mensaje mensaje-exito">
          <i class="fas fa-check-circle"></i>
          <div>
            <strong>Reporte enviado</strong>
            <p>Hemos recibido tu reporte y lo estamos revisando. Te contactaremos si necesitamos más información.</p>
          </div>
        </div>
      `;
      this.reset();
    });
  }
}

function cambiarTema(tema) {
  document.body.className = '';
  if (tema !== 'default') {
    document.body.classList.add(`theme-${tema}`);
  }
  
  // Guardar preferencia de tema
  localStorage.setItem('temaPreferido', tema);
}