# Use Node.js as base image
FROM node:20-alpine

# Set working directory
WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy project files
COPY . .

# Build the application
RUN npm run build

# Install serve to run the application
RUN npm install -g serve

# Expose port 4200
EXPOSE 4200

# Start the application
CMD ["serve", "-s", "dist/demo", "-l", "4200"]