# xml-loader

Необходимо настроить postgresql:
jdbc.url=jdbc:postgresql://localhost:5432/postgres
jdbc.user=postgres
jdbc.password=postgres
jdbc.maxActive=10

либо в файле application.properties(или через переменные окружения) указать свои настройки.

В папке с проектом {your-path}/xml-loader:
1. mvn clean package
2. запустить wildfly и выполнить деплой файла Loader.war через админку из {your-path}/xml-loader/target
  {your-path} - папка с исходным кодом
3. для загрузки файлов использовать веб-клиент: {yourServer}:{port}/{appContext}/index.html
  {yourServer} - сервер, на котором расположен wildfly
  {port} - по умолчанию 8080, если нужен другой указать -Dserver.port=8091
  {appContext} - по умолчанию Loader
4. свойство temp.file.storage необходимо для временного хранения больших файлов, чтобы при загрузке не держать их в памяти.
