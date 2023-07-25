package de.essagl.tuya.aircal.adapter;

import de.essagl.tuya.aircal.adapter.model.ControlParameter202;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class CParameterDecoderTest {
    @Test
    public void test() {
        // example value received for c01_c30_reading parameter
        String c1c30  = "AL4AuAC8gACAAAC4AK8AvAHlAAD//gAAAEcARwAAAAAAAAFeAV4AAAAAABYA9AI+APoBDgC+AL4AAAAA";
        // example value received for c31_c56_reading parameter
        String c31c56 ="AAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAAAAAAAWAAwALQACAAAAAABQAAABDgAABeUAAA==";

        byte[] c1c30DecodedBytes = Base64.getDecoder().decode(c1c30);
        byte[] c31c56DecodedBytes = Base64.getDecoder().decode(c31c56);
        // read values into list
        List<Integer> results = new ArrayList<>();
        for (int i=0;  i< c1c30DecodedBytes.length; i++ ){
            if (i % 2 != 0) {
                results.add(((c1c30DecodedBytes[i -1] & 0xff) << 8) | (c1c30DecodedBytes[i] & 0xff));
            }
        }

        Assert.isTrue(getInt(c1c30DecodedBytes,0) == results.get(0));
        Assert.isTrue(getInt(c1c30DecodedBytes,58) == results.get(29));


        ControlParameter202 controlParameter202 = new ControlParameter202(c1c30DecodedBytes,c31c56DecodedBytes);

        controlParameter202.getC26();


    }


    public static int getInt(byte[] arr, int off) {
        return arr[off]<<8 &0xFF00 | arr[off+1]&0xFF;
    }
}
