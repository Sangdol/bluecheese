const euCookieName = 'complianceCookie';
const euCookieValue = 'on';

function createDiv() {
  const $cookieDiv = $
    var bodytag = document.getElementsByTagName('body')[0];
    var div = document.createElement('div');
    div.setAttribute('id','cookie-law');
    div.setAttribute('class','animate__animated animate__backInUp');
    div.innerHTML = `
    <p>
      <a href="#" onclick="removeMe()">
        <img class="close-x" src="img/close.svg">
      </a>
      This site uses cookies.
      &nbsp;
      &nbsp;
      <button class="button-primary" onclick="removeMe()">OK</button>
    </p>`;

    bodytag.insertBefore(div,bodytag.firstChild);
}

function removeMe(){
  Cookies.set(euCookieName, euCookieValue, { expires: 365 });

  $('#cookie-law').remove();
}

$(window).on("load", function() {
  if (Cookies.get(euCookieName) != euCookieValue) {
    createDiv();
  }
});
