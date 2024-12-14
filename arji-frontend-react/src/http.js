export const login = async function(userDetails) {

    const response = await fetch('http://localhost:8080/api/auth/login', {
        method: "POST",
        headers: {
            "Content-Type": "application/json", // Inform the server about the JSON format
        },
        body: JSON.stringify(userDetails)
    });

    return response;
    
}