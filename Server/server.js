const net = require('net');
const fs = require('fs');
const path = require('path');

const HOST = '127.0.0.1';
const PORT = 8080;
const ProjectDir = path.resolve(process.cwd(), "./database");

net.createServer((socket) => {

    console.log('CONNECTED: ' + socket.remoteAddress + ':' + socket.remotePort);

    socket.on('error', (err) => {
        socket.destroy(err);
    })

    socket.on('data', (data) => {
        let res = data.toString();
        if (res.startsWith('|')) {
            let code = res.split('|')[1];
            let inputData = res.split('|')[2];
            let documentLocation = path.resolve(ProjectDir, code + ".html");

            if (fs.existsSync(documentLocation)) {
                if (res == ("|" + code + "|\r\n")) {
                    let docmentContent = fs.readFileSync(documentLocation, { encoding: "utf-8" });
                    console.log(docmentContent.toString());
                    socket.write(docmentContent.toString());
                }
                else if (code != '') {
                    fs.writeFileSync(documentLocation, inputData, { encoding: "utf-8" });
                    socket.write(inputData);
                }
            }
            else {
                socket.write("");
            }
        } else {
            socket.write("");
        }
    });


}).listen(PORT, HOST);


console.log('Server listening on ' + HOST + ':' + PORT);
