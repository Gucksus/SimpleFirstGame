#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform vec3 u_flashColor;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

vec3 glowFlash(vec4 pixelColor) {
    return clamp(pixelColor.rgb + vec3(0.5), vec3(0), vec3(1));
}

vec3 invertFlash(vec4 pixelColor) {
    return vec3(1) - pixelColor.rgb;
}

void main() {
        vec4 pixelColor = texture2D(u_texture, v_texCoords);
        if (pixelColor.a < 0.01) discard;
        vec3 finalRGB = glowFlash(pixelColor);
        gl_FragColor = vec4(finalRGB, 1);
}