// Mobile detection
// Ipod, Iphone, Ipad, Androïd, Windows phone
var isMobile = {
    Android: function() {
        return navigator.userAgent.match(/Android/i);
    },
    BlackBerry: function() {
        return navigator.userAgent.match(/BlackBerry|BB10/i);
    },
    iOS: function() {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
    },
    Windows: function() {
        return navigator.userAgent.match(/IEMobile/i);
    },
    Nokia: function() {
        return navigator.userAgent.match(/Nokia/i);
    },
    any: function() {
        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Windows() || isMobile.Nokia());
    }
};
