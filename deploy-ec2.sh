#!/bin/bash
# ==========================================
# FinFlow AWS EC2 Deployment Script
# OS: Amazon Linux 2023 / Amazon Linux 2
# ==========================================

# 1. Update system packages
echo "==> Updating system packages..."
sudo yum update -y

# 2. Install Git
echo "==> Installing Git..."
sudo yum install git -y

# 3. Install Docker
echo "==> Installing Docker..."
sudo yum install docker -y

# 4. Start and enable Docker service
echo "==> Starting Docker service..."
sudo systemctl start docker
sudo systemctl enable docker

# 5. Add ec2-user to docker group so you can run docker without sudo
echo "==> Adding ec2-user to docker group..."
sudo usermod -aG docker ec2-user

# 6. Install Docker Compose and Buildx
echo "==> Installing Docker Compose..."
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

echo "==> Installing Docker Buildx..."
sudo mkdir -p /usr/libexec/docker/cli-plugins
sudo curl -SL https://github.com/docker/buildx/releases/download/v0.17.1/buildx-v0.17.1.linux-amd64 -o /usr/libexec/docker/cli-plugins/docker-buildx
sudo chmod +x /usr/libexec/docker/cli-plugins/docker-buildx

# 7. Clone the repositories
echo "==> Cloning FinFlow backend and frontend repositories..."
cd /home/ec2-user
git clone https://github.com/Raviteja7659/spring-app-backend.git
git clone https://github.com/Raviteja7659/spring-app-frontend.git

# 8. Add Swap Space (Crucial for t2.micro to prevent Out-Of-Memory crashes)
echo "==> Setting up 2GB Swap Space..."
sudo fallocate -l 2G /swapfile
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile

# 9. Spin up the Database and Backend
echo "==> Starting the Database and Backend..."
cd /home/ec2-user/spring-app-backend
sudo /usr/local/bin/docker-compose up -d --build

# 10. Configure and Start the Frontend
echo "==> Configuring Frontend for Public IP..."
cd /home/ec2-user/spring-app-frontend

# Dynamically fetch the EC2 instance's Public IP address
PUBLIC_IP=$(curl -s http://checkip.amazonaws.com)
echo "Detected Public IP: $PUBLIC_IP"

# Automatically update the frontend files to use the real Public IP instead of localhost
sed -i "s/localhost/$PUBLIC_IP/g" src/api.js
sed -i "s/localhost/$PUBLIC_IP/g" docker-compose.yml

echo "==> Starting the Frontend..."
sudo /usr/local/bin/docker-compose up -d --build

echo "=================================================="
echo "Deployment Complete!"
echo "Your application is officially live!"
echo "Visit: http://$PUBLIC_IP:5173"
echo "=================================================="
