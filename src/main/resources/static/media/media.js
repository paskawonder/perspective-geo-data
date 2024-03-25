var map = L.map('map').setView([52.3676, 4.9041], 1);
L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager_labels_under/{z}/{x}/{y}{r}.png', { maxZoom: 19 }).addTo(map);

map.on('click', click);

function click(e) {
    var isActive = false;
    if (document.getElementById('pics').firstChild) {
        isActive = true;
    }
    if (document.getElementById('pics') != e.target) {
        removeAllChildNodes(document.getElementById('pics'));
    }
    var action_buttons = document.getElementById('action_buttons');
    if (!isActive && action_buttons.style.display === 'none') {
        action_buttons.isActive = true;
        action_buttons.style.display='block';
        action_buttons.style.zIndex=400;
        action_buttons.style.left = e.originalEvent.clientX;
        action_buttons.style.top = e.originalEvent.clientY;
        action_buttons.latlng = e.latlng;
    } else {
        action_buttons.style.display = 'none';
    }
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
    xhr.open('POST', 'http://localhost:8080/media/get', false);
    xhr.setRequestHeader('Content-type', 'application/json');
    var latlng = e.parentNode.latlng;
    xhr.send(JSON.stringify({'lat': latlng.lat, 'lon': latlng.lng}));
    var imgs = JSON.parse(xhr.responseText);
    var pics = document.getElementById('pics');
    for (let i = 0; i < imgs.length; i++) {
        const div = document.createElement('div');
        pics.append(div);
        const img = document.createElement('img');
        img.src = "data:image/jpeg;base64," + imgs[i];
        div.append(img);
    }
    pics.style.left = e.parentNode.style.left;
    pics.style.top = e.parentNode.style.top;
    clear(e);
    pics.style.zIndex = 400;
}

function clear(e) {
    e.parentNode.style.display = 'none';
}

function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}