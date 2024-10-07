
let list = tag

const handleButtonClick = (tag) => {
    let list 
    if(tag === "All") {
        list = data
    } else {
        list = data.filter(element => element.tag.includes(tag))
    }
}

// script.js

// 각 버튼에 클릭 이벤트 추가
document.getElementById('button1').addEventListener('click', function() {
    handleButtonClick('All')
});

document.getElementById('button2').addEventListener('click', function() {
    handleButtonClick('Dinner')
});

document.getElementById('button3').addEventListener('click', function() {
    handleButtonClick('All')
});

document.getElementById('button4').addEventListener('click', function() {
    handleButtonClick('All')
});

document.getElementById('button5').addEventListener('click', function() {
    handleButtonClick('All')
});


const data = [
    {   
        name : "Dinner A",
        price : 1000,
        tag : ["Dinner"],
        image : "",
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum convallis turpis, ac vulputate felis mattis sit amet." 
    },
    {   
        name : "Dinner B",
        price : 500,
        tag : ["Dinner"],
        image : "",
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum convallis turpis, ac vulputate felis mattis sit amet." 
    },
    {   
        name : "Dinner C",
        price : 2000,
        tag : ["Dinner"],
        image : "",
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum convallis turpis, ac vulputate felis mattis sit amet." 
    },
    {   
        name : "Dinner D",
        price : 2500,
        tag : ["Dinner"],
        image : "",
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum convallis turpis, ac vulputate felis mattis sit amet." 
    },
    {   
        name : "Breakfast A",
        price : 2000,
        tag : ["Breakfast"],
        image : "",
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum convallis turpis, ac vulputate felis mattis sit amet." 
    },
    {   
        name : "Breakfast B",
        price : 1000,
        tag : ["Breakfast"],
        image : "",
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum convallis turpis, ac vulputate felis mattis sit amet." 
    },
    {   
        name : "Lunch A",
        price : 1000,
        tag : ["Lunch"],
        image : "",
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum convallis turpis, ac vulputate felis mattis sit amet." 
    },
    {   
        name : "Lunch B",
        price : 1000,
        tag : ["Lunch"],
        image : "",
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum convallis turpis, ac vulputate felis mattis sit amet." 
    },
    {   
        name : "Lunch C",
        price : 500,
        tag : ["Lunch"],
        image : "",
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum convallis turpis, ac vulputate felis mattis sit amet." 
    },
    {   
        name : "Shakes A",
        price : 500,
        tag : ["Shakes"],
        image : "",
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum convallis turpis, ac vulputate felis mattis sit amet." 
    },
    {   
        name : "Shakes B",
        price : 2000,
        tag : ["Shakes"],
        image : "",
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum convallis turpis, ac vulputate felis mattis sit amet." 
    },
    {   
        name : "Shakes C",
        price : 2000,
        tag : ["Shakes"],
        image : "",
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum convallis turpis, ac vulputate felis mattis sit amet." 
    },
    {   
        name : "Shakes D",
        price : 2000,
        tag : ["Shakes"],
        image : "",
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum convallis turpis, ac vulputate felis mattis sit amet." 
    },
]