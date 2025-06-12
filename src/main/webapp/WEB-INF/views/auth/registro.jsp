<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #3498db, #8e44ad);
            margin: 0;
            padding: 0;
        }

        .container {
            width: 380px;
            margin: 80px auto;
            background: #fff;
            padding: 30px 40px;
            border-radius: 15px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #333;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"],
        input[type="tel"],
        input[type="number"],
        select {
            width: 100%;
            padding: 12px;
            margin: 8px 0 16px;
            border: 1px solid #ccc;
            border-radius: 8px;
            box-sizing: border-box;
        }

        select {
            appearance: none;
            background: #fff;
        }

        label {
            font-size: 14px;
            color: #333;
        }

        .checkbox-container {
            display: flex;
            align-items: center;
            margin-bottom: 16px;
        }

        .checkbox-container input {
            margin-right: 10px;
        }

        button {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
        }

        button:hover {
            background-color: #45a049;
        }

        .social-buttons {
            display: flex;
            justify-content: space-between;
            margin-top: 25px;
        }

        .social-button {
            width: 22%;
            padding: 10px;
            background-color: #eee;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 20px;
            text-align: center;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            transition: transform 0.2s ease;
        }

        .social-button:hover {
            transform: scale(1.1);
        }

    </style>
</head>
<body>

    <div class="container">
        <h2>Registro</h2>
        <form action="RegistrarUsuarioServlet" method="post">
            <input type="text" name="nombre" placeholder="Nombre" required>
            <input type="text" name="apellido" placeholder="Apellido" required>
            <input type="email" name="correo" placeholder="Correo electrónico" required>
            <input type="password" name="contrasena" placeholder="Contraseña" required>
            <input type="tel" name="telefono" placeholder="Teléfono" required>
            <input type="number" name="edad" placeholder="Edad" required>
            
            <select name="sexo" required>
                <option value="" disabled selected>Seleccione su sexo</option>
                <option value="hombre">Hombre</option>
                <option value="mujer">Mujer</option>
                <option value="no_decir">Prefiero no decirlo</option>
            </select>

            <div class="checkbox-container">
                <input type="checkbox" name="terminos" required>
                <label for="terminos">Acepto los términos y condiciones</label>
            </div>

            <button type="submit">Registrarse</button>
        </form>

        <div class="social-buttons">
            <button class="social-button">?</button> <!-- Facebook -->
            <button class="social-b

