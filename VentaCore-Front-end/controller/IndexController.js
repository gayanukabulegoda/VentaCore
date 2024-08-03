$(document).ready(function () {
  $("#homePage").show();

  $(".nav-link").click(function (event) {
    event.preventDefault();

    $("section").hide();

    var targetSection = $(this).attr("href");

    $(targetSection).show();
    switch (targetSection) {
      case "#CustomerManage":
        $(".sectionName").text("Customer Manage");
        break;
      case "#ItemManage":
        $(".sectionName").text("Item Manage");
        break;
      case "#OrderManage":
        $(".sectionName").text("Order Manage");
        break;
      default:
        $(".sectionName").text("Home Page");
    }
  });
});

{
  let currentSlide = 0;
  const slideInterval = 3000;
  let autoSlide;

  function showSlide(index) {
    const $slides = $(".carousel-item");
    const totalSlides = $slides.length;

    if (index >= totalSlides) {
      currentSlide = 0;
    } else if (index < 0) {
      currentSlide = totalSlides - 1;
    } else {
      currentSlide = index;
    }

    const offset = -currentSlide * 100;
    $(".carousel-inner").css("transform", "translateX(" + offset + "%)");

    $slides.each(function (i) {
      $(this).toggleClass("active", i === currentSlide);
    });
  }

  function nextSlide() {
    showSlide(currentSlide + 1);
  }

  function prevSlide() {
    showSlide(currentSlide - 1);
  }

  function startAutoSlide() {
    autoSlide = setInterval(nextSlide, slideInterval);
  }

  function stopAutoSlide() {
    clearInterval(autoSlide);
  }

  showSlide(currentSlide);
  startAutoSlide();

  $(".carousel").mouseenter(stopAutoSlide).mouseleave(startAutoSlide);

  $(".prev").click(function () {
    prevSlide();
  });

  $(".next").click(function () {
    nextSlide();
  });
}
