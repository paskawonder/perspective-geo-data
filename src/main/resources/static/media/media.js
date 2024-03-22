var map = L.map('map').setView([52.3676, 4.9041], 1);
L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager_labels_under/{z}/{x}/{y}{r}.png', { maxZoom: 19 }).addTo(map);

map.on('click', uploadFile);

function uploadFile(e) {
    document.getElementById('file').addEventListener('change', function() {
        var meta = JSON.stringify({'coordinates': {'lat': e.latlng.lat, 'lon': e.latlng.lng}});
        const formData = new FormData();
        formData.append('meta', meta);
        formData.append('file', file.files[0]);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://localhost:8080/media', true);
        xhr.send(formData);
    });
    document.getElementById('file').click();
}
