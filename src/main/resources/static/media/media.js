var map = L.map('map').setView([52.3676, 4.9041], 1);
L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager_labels_under/{z}/{x}/{y}{r}.png', { maxZoom: 19 }).addTo(map);

map.on('click', click);

function click(e) {
    var action_buttons = document.getElementById('action_buttons');
    action_buttons.style.display='block';
    action_buttons.style.zIndex=400;
    action_buttons.style.left = e.originalEvent.clientX;
    action_buttons.style.top = e.originalEvent.clientY;
    action_buttons.latlng = e.latlng;
}

function upload(e) {
    document.getElementById('file').addEventListener('change', function() {
        const formData = new FormData();
        formData.append('meta', JSON.stringify({'coordinates': e.parentNode.latlng}));
        formData.append('file', file.files[0]);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://localhost:8080/media', true);
        xhr.send(formData);
    });
    document.getElementById('file').click();
    e.parentNode.style.display = 'none';
}

function view(e) {
    const xhr = new XMLHttpRequest();
    xhr.overrideMimeType('application/json');
    xhr.open('POST', 'http://localhost:8080/media/get', false);
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(JSON.stringify(e.parentNode.latlng));
    var imgs = JSON.parse(xhr.responseText);
    for (let i = 0; i < imgs.length; i++) {
        const img = document.createElement('img');
        img.src = 'data:image/jpeg;base64,' + imgs[i].payload;
        latlng = imgs[i].coordinates;
        L.imageOverlay(img, [[latlng.lat - 1, latlng.lng - 1], [latlng.lat + 1, latlng.lng + 1]]).addTo(map);
    }
    e.parentNode.style.display = 'none';
}
