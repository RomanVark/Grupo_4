# Grupo 4 — Gestión de solicitudes

Aplicación de escritorio JavaFX para registrar clientes, consultar solicitudes y transferir información entre formularios. El proyecto utiliza Maven, FXML compatible con Scene Builder, Lombok y una arquitectura separada por responsabilidades.

## Requisitos

- JDK 17
- IntelliJ IDEA
- Scene Builder (para editar visualmente los archivos FXML)

## Ejecución

En Windows:

```powershell
.\mvnw.cmd clean javafx:run
```

En Linux o macOS:

```bash
bash mvnw clean javafx:run
```

El ejercicio valida que usuario y contraseña no estén vacíos; no incorpora credenciales reales ni autenticación remota.

## Estructura

```text
src/main/java/ni/edu/uam/grupo_4/
├── config/       # Contexto compartido de la aplicación
├── controller/   # Controladores JavaFX de cada ventana
├── crud/         # Contrato CRUD genérico
├── dao/          # Acceso a datos de clientes
├── model/        # Entidades con Lombok
└── util/         # Navegación, vistas y diálogos

src/main/resources/ni/edu/uam/grupo_4/
├── css/          # Estilos de la interfaz
└── view/         # Archivos FXML editables con Scene Builder
```

## Flujo de navegación

1. Inicio de sesión → menú principal.
2. Menú principal → registro de cliente → consulta de clientes.
3. Consulta → doble clic en un registro → detalle y edición.
4. Todas las ventanas ofrecen una acción para regresar o cerrar.

## Funcionalidades implementadas

- Validación de acceso y confirmación al salir.
- `MenuBar`, `ToolBar`, botones de acceso y `ContextMenu`.
- Registro con `TextField`, `ComboBox`, `DatePicker`, `RadioButton`, `ToggleGroup` y `CheckBox`.
- Selección y vista previa de fotografía con `FileChooser` e `ImageView`.
- Consulta mediante `TableView`, búsqueda, doble clic, edición y eliminación.
- CRUD completo mediante `CrudRepository`, `ClientDao` e `InMemoryClientDao`.
- Transferencia del cliente seleccionado a la ventana modal de detalle.
- Eventos `ActionEvent`, `MouseEvent` y `KeyEvent` (`Enter`, `Esc`, `Delete` y `Ctrl+Enter`).
- Alertas de información, advertencia, error y confirmación.
- `Dialog` de resumen del sistema.
- `DirectoryChooser` conectado a la exportación real de `clientes.csv`.
- Prueba unitaria del ciclo CRUD y verificación automática con GitHub Actions.

## Persistencia

Los datos se mantienen durante la ejecución mediante un DAO en memoria compartido. Esta decisión cumple el paso de datos entre ventanas sin agregar una base de datos que no forma parte del enunciado. La interfaz `ClientDao` permite sustituir la implementación por JDBC, archivos o una API sin modificar los controladores.

## Apertura en Scene Builder

Los diseños se encuentran en `src/main/resources/ni/edu/uam/grupo_4/view`. Desde IntelliJ, haga clic derecho sobre un archivo `.fxml` y seleccione **Open in Scene Builder**.
