// serverfix.rs

use std::net::TcpListener;
use std::io::{Read, Write, Result};
use std::thread;

// Example 1: A simple TCP server that handles incoming connections
pub fn start_server(address: &str) -> Result<()> {
    let listener = TcpListener::bind(address)?;
    println!("Server running on {}", address);

    for stream in listener.incoming() {
        match stream {
            Ok(mut stream) => {
                thread::spawn(move || {
                    handle_client(&mut stream).unwrap_or_else(|error| {
                        eprintln!("Failed to handle client: {}", error);
                    });
                });
            }
            Err(e) => eprintln!("Failed to accept connection: {}", e),
        }
    }
    Ok(())
}

// Example 2: Handling client connections
fn handle_client(stream: &mut impl Read) -> Result<()> {
    let mut buffer = [0; 1024];
    let bytes_read = stream.read(&mut buffer)?;
    if bytes_read > 0 {
        let response = "HTTP/1.1 200 OK\r\nContent-Length: 13\r\n\r\nHello, world!";
        stream.write_all(response.as_bytes())?;
    }
    Ok(())
}

// Example 3: Implementing error handling for TCP server initialization
pub fn init_server(address: &str) -> Result<()> {
    match TcpListener::bind(address) {
        Ok(listener) => {
            println!("Server successfully bound to {}", address);
            Ok(())
        }
        Err(e) => {
            eprintln!("Error binding server to address {}: {}", address, e);
            Err(e)
        }
    }
}

// Example 4: Graceful shutdown
pub fn shutdown_server() {
    println!("Shutting down server...");
    // Implement shutdown logic if necessary
}

// Example 5: Parsing command-line arguments for server configuration
use std::env;

pub fn parse_args() -> String {
    let args: Vec<String> = env::args().collect();
    if args.len() > 1 {
        args[1].clone()
    } else {
        "127.0.0.1:8080".to_string() // Default address
    }
}

// Example 6: Improved logging
pub fn log_message(message: &str) {
    println!("[INFO] {}", message);
}

