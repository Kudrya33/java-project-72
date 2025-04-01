@import hexlet.code.model.Url
@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.urls.UrlsPage

@param UrlsPage page

@template.homePage(page = page,
    content = @`
    <section class="flex-grow-1 p-5">
        <div class="container-lg mt-8">
            <h1 class="text-center">Сайты</h1>
            <table class="table table-bordered table-hover mt-3">
                <thead>
                    <tr>
                        <th class="col-1">id</th>
                        <th class="col-3">сайт</th>
                        <th class="col-1">Последняя проверка</th>
                        <th class="col-3">дата проверки</th>
                    </tr>
                </thead>
                <tbody>
                    @for(Url url : page.getUrls())
                        <tr>
                            <td class="col-1">${url.getId()}</td>
                            <td class="col-3"><a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a></td>
                            <td class="col-1">${page.getChecklist().get(0).get(url.getId())}</td>
                            <td class="col-3">${page.getChecklist().get(1).get(url.getId())}</td>
                        </tr>
                    @endfor
                </tbody>
            </table>
        </div>
    </section>
`)