@import hexlet.code.model.UrlCheck
@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.urls.UrlPage

@param UrlPage page

@template.homePage(page = page,
    content = @`
    <section class="flex-grow-1 p-5">
        <div class="container-lg mt-8">
            <table class="table table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <th class="col-2">id</th>
                    <th class="col-3">сайт</th>
                    <th class="col-3">Дата Создания</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="col-2">${page.getUrl().getId()}</td>
                        <td class="col-3"><a href="${page.getUrl().getName()}">${page.getUrl().getName()}</a></td>
                        <td class="col-3">${String.valueOf(page.getUrl().getCreatedAt()).substring(0, 16)}</td>
                    </tr>
                </tbody>
            </table>
            <div>
                <form action="${NamedRoutes.urlChecks(page.getUrl().getId())}" method="post">
                    <input type="submit" value="обновить статус" class="btn btn-primary">
                </form>
            </div>
            <div class="col">
                <h1 class="text-center">Проверки</h1>
                <table class="table table-bordered table-hover mt-3">
                    <thead>
                        <tr>
                            <th class="col-1">ID</th>
                            <th class="col-1">Код ответа</th>
                            <th class="col-2">title</th>
                            <th class="col-2">h1</th>
                            <th class="col-2">description</th>
                            <th class="col-1">Дата проверки</th>
                        </tr>
                    </thead>
                    <tbody>
                        @if(page.getUrl() != null && page.getUrl().getUrlCheck() != null)
                            @for(UrlCheck check : page.getUrl().getUrlCheck())
                                <tr>
                                    <td class="col-1">${check.getId()}</td>
                                    <td class="col-1">${check.getStatusCode()}</td>
                                    <td class="col-2">${check.getTitle()}</td>
                                    <td class="col-2">${check.getH1()}</td>
                                    <td class="col-2">${check.getDescription()}</td>
                                    <td class="col-1">${String.valueOf(check.getCreatedAt()).substring(0,16)}</td>
                                </tr>
                            @endfor
                        @endif
                    </tbody>
                </table>
            </div>
        </div>
    </section>
`)