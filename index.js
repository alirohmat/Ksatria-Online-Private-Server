addEventListener('fetch', event => {
  event.respondWith(handleRequest(event.request))
})

async function handleRequest(request) {
  // Nama server atau IP address
  const serverName = "hsindo.teamobi.com";
  
  // Port yang digunakan oleh server (ubah sesuai kebutuhan)
  const port = 443;
  
  // Data tambahan lainnya (misalnya status)
  const status = "0,0";
  
  // Format data yang akan dikirimkan
  const responseData = `Indo Naga:${serverName}:${port}:${status}`;
  
  // Mengembalikan respons dalam format yang diminta
  return new Response(responseData, {
    headers: { 'Content-Type': 'text/plain' }
  });
}
