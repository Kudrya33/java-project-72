@import hexlet.code.dto.urls.UrlPage
@param UrlPage page

@template.homePage(
    content = @`
        <h1>${page.getUrl().getName()}</h1>
        @if (page != null && page.getFlash() != null)
            <div class="rounded-0 m-0 alert alert-${page.getFlashType()}" role="alert">
                ${page.getFlash()}
            </div>
        @endif
    `
)