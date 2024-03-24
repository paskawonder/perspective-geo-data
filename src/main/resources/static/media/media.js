var map = L.map('map').setView([52.3676, 4.9041], 1);
L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager_labels_under/{z}/{x}/{y}{r}.png', { maxZoom: 19 }).addTo(map);

map.on('click', display);

function display(e) {
    var action_buttons = document.getElementById('action_buttons');
    action_buttons.style.display='block';
    action_buttons.style.zIndex=400;
    action_buttons.style.left = e.originalEvent.clientX;
    action_buttons.style.top = e.originalEvent.clientY;
    action_buttons.latlng = e.latlng;
}

function upload(e) {
    document.getElementById('file').addEventListener('change', function() {
        var latlng = e.parentNode.latlng;
        var meta = JSON.stringify({'coordinates': {'lat': latlng.lat, 'lon': latlng.lng}});
        const formData = new FormData();
        formData.append('meta', meta);
        formData.append('file', file.files[0]);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://localhost:8080/media', true);
        xhr.send(formData);
    });
    document.getElementById('file').click();
    clear(e);
}

function view(e) {
    const xhr = new XMLHttpRequest();
    xhr.overrideMimeType('application/json');
    xhr.open('GET', 'http://localhost:8080/media', false);
    xhr.send(null);
    var imgs = JSON.parse(xhr.responseText);
    for (let i = 0; i < imgs.length; i++) {
        const img = document.createElement('img');
        img.src = "data:image/jpeg;base64," + imgs[i];
        document.querySelector(`body`).append(img);
    }
    clear(e);
}

function clear(e) {
    e.parentNode.style.display = 'none';
}