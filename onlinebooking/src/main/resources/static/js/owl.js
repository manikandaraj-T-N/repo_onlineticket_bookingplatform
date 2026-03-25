$(document).ready(function(){
    $(".owl-carousel").owlCarousel({
        items: 1,
        loop: true,
        autoplay: true,
        nav: true,
        dots: true
    });
});

// ==========================banner tabs forms==========================
function showForm(event, type) {

    // hide all forms
    document.querySelectorAll('.form').forEach(f => {
        f.classList.remove('active');
    });

    // remove active from tabs
    document.querySelectorAll('.tab').forEach(t => {
        t.classList.remove('active');
    });

    // show selected form
    document.getElementById(type).classList.add('active');

    // activate clicked tab
    event.target.classList.add('active');
}


$('.experience-carousel').owlCarousel({
    loop: true,
    margin: 20,

    nav: false,          
    dots: true,         

    autoplay: true,     
    autoplayTimeout: 1000,
    autoplayHoverPause: true,

    responsive: {
        0: { items: 1 },
        576: { items: 2 },
        768: { items: 3 },
        992: { items: 4 }
    }
});



$('.owl-customers').owlCarousel({
    loop: true,
    margin: 20,
    dots: true,
    nav: false,
    autoplay: true,
    autoplayTimeout: 3000,
    autoplayHoverPause: true,
    responsive: {
        0: { items: 1 },
        768: { items: 2 },
        1024: { items: 2 }
    }
});



$('.owl-airlines').owlCarousel({
    loop: true,
    margin: 30,
    nav: false,
    dots: false,
    autoplay: true,
    autoplayTimeout: 1000,
    responsive: {
        0:    { items: 2 },
        576:  { items: 3 },
        768:  { items: 4 },
        1024: { items: 5 }
    }
});