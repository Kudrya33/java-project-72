@import hexlet.code.dto.urls.UrlPage
@param UrlPage page

@template.homePage(
    content = @`
        <h1>${page.getUrl().getName()}</h1>
    `
)