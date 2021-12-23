/* jshint esversion: 6 */

document.getElementById('searchbutton').onclick = () => {
    fetch("/search?q=" + document.getElementById('searchbox').value)
    .then((response) => response.json())
    .then((data) => {
        document.getElementById("responsesize").innerHTML = 
            "<p>" + data.length + " websites retrieved</p>";

            // if no pages match the query it outputs "No web page contains the query word."
            if(data.length === 0){
                document.getElementById("noresults").innerHTML = 
                 "<p> No web page contains the query word. </p>";
            } else document.getElementById("noresults").innerHTML = "";

        let results = data.map((page) =>
            `<li><a href="${page.url}">${page.title}</a></li>`)
            .join("\n");
        document.getElementById("urllist").innerHTML = `<ul>${results}</ul>`;
    });
};
