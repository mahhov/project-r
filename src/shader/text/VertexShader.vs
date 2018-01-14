#version 330 core

layout (location=0) in vec2 position;
layout (location=1) in vec2 textureCoordinate;
layout (location=2) in vec4 color;

out vec2 fTextureCoordinate;
out vec4 fColor;

void main() {
    gl_Position = vec4(position, -1, 1);
    fTextureCoordinate = textureCoordinate;
    fColor = color;
}