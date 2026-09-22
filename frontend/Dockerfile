# ------------------------------------------------------------------------------
# Stage 1: Build Angular application
# ------------------------------------------------------------------------------

FROM node:22-alpine AS build

WORKDIR /app

COPY package*.json ./

RUN npm ci

COPY . .

RUN npm run build


# ------------------------------------------------------------------------------
# Stage 2: Serve Angular application with Nginx
# ------------------------------------------------------------------------------

FROM nginx:alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=build /app/dist/origen/browser /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
