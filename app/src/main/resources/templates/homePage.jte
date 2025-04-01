@import gg.jte.Content
@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.BasePage


@param BasePage page = null
@param Content content

<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Главная</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
                crossorigin="anonymous"></script>
    </head>
    <body class="d-flex flex-column min-vh-100">
        <header class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <h1 class="navbar-brand">Анализатор страниц</h1>
                <div class="collapse navbar-collapse">
                    <nav class="navbar-nav">
                        <a class="nav-link" href="${NamedRoutes.homePath()}">Главная</a>
                        <a class="nav-link" href="${NamedRoutes.urlsPath()}">Сайты</a>
                    </nav>
                </div>
            </div>
        </header>
        @if (page != null && page.getFlash() != null)
            <div class="rounded-0 m-0 alert alert-${page.getFlashType()}" role="alert">
                ${page.getFlash()}
            </div>
        @endif
        ${content}
    <footer class="footer border-top py-3 mt-5 bg-light">
        <p class="text-center">Created by <a href="https://github.com/Kudrya33">Kudrya33</a></p>
    </footer>
    </body>
</html>