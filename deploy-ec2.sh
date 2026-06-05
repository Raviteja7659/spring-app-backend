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

# 6. Install Docker Compose
echo "==> Installing Docker Compose..."
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

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

# 9. Spin up the applications
echo "==> Starting the Database and Backend..."
cd /home/ec2-user/spring-app-backend
sudo /usr/local/bin/docker-compose up -d --build

echo "==> Starting the Frontend..."
cd /home/ec2-user/spring-app-frontend
sudo /usr/local/bin/docker-compose up -d --build

echo "=================================================="
echo "Deployment Complete!"
echo "Backend is running on port 8082"
echo "Frontend is running on port 5173"
echo "Database is running on port 5432"
echo "=================================================="
