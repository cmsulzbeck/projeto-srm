#!/bin/bash

# Build the Docker image
echo "Building Docker image..."
docker build -t er-diagram -f Dockerfile.diagram .

# Run the container and copy the generated diagram
echo "Generating diagram..."
docker run --rm -v "$(pwd):/diagram" er-diagram

if [ $? -eq 0 ]; then
    echo "Diagram generated successfully: er-diagram.png"
else
    echo "Error generating diagram."
    exit 1
fi 