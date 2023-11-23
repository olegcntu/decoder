# ADS-B Decoder

## Overview
Welcome to the ADS-B Decoder project! This Java-based application is designed for decoding Automatic Dependent Surveillance–Broadcast (ADS-B) messages from aircraft. The project leverages multithreading to efficiently process large volumes of ADS-B messages in parallel.

## Getting Started
### Clone the Repository

git clone https://github.com/your-username/adsb-decoder.git

### Build the Project
Build the project using your preferred Java build tool, such as Maven or Gradle.

## Usage
### Ensure Java is Installed
Ensure that you have Java installed on your machine.

### Adjust Configuration Parameters
Adjust the configuration parameters in the config.properties file:
- file.path: Path to the file containing ADS-B messages.
- segment.size: Size of each segment for parallel processing.
- num.threads: Number of threads for parallel decoding.

### Run the Application
Run the App class. This will read, decode, and output the ADS-B messages.

## Configuration
The config.properties file contains configurable parameters for the application:

file.path=/path/to/your/adsb/messages segment.size=14 num.threads=4
Adjust these values according to your specific requirements.

## Project Components
- Aircraft Decoder (DefaultAircraftDecoder): Decodes ADS-B messages and creates Aircraft objects.
- List Splitter (DefaultListSplitter): Splits a list into a specified number of sublists.
- Parallel File Reader (ParallelFileReader): Reads raw ADS-B messages from a file in parallel.
- App Processor (AppProcessor): Collects and merges decoding results from multiple decoding tasks.
- App Executor (AppExecutor): Manages the execution of decoding tasks using an ExecutorService.
- App (App): Main class orchestrating the application workflow.


Thank you for considering this ADS-B Decoder project. 

