package com.lynxdeer.lynxlib.utils.display;

import com.lynxdeer.lynxlib.utils.display.physics.PhysicsObject;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;

public class DisplayUtils {

	public static ArrayList<PhysicsObject> physicsObjects = new ArrayList<>();
	
	public static void spawnPhysicsObject(Location loc, ItemStack item) {
		new PhysicsObject(loc, item);
	}
	
	// Thanks, ChatGPT, now I don't have to do math!
	public static Quaternionf eulerToQuaternion(float x, float y, float z) {
		
		float cosX = (float) Math.cos(x / 2);
		float sinX = (float) Math.sin(x / 2);
		float cosY = (float) Math.cos(y / 2);
		float sinY = (float) Math.sin(y / 2);
		float cosZ = (float) Math.cos(z / 2);
		float sinZ = (float) Math.sin(z / 2);
		
		float qx = sinX * cosY * cosZ - cosX * sinY * sinZ;
		float qy = cosX * sinY * cosZ + sinX * cosY * sinZ;
		float qz = cosX * cosY * sinZ - sinX * sinY * cosZ;
		float qw = cosX * cosY * cosZ + sinX * sinY * sinZ;
		
		return new Quaternionf(qx, qy, qz, qw);
	}
	public static float[] quaternionToEuler(Quaternionf original) {
		
		float x = original.x;
		float y = original.y;
		float z = original.z;
		float w = original.w;
		
		// Roll (x-axis rotation)
		float sinr_cosp = 2.0f * (w * x + y * z);
		float cosr_cosp = 1.0f - 2.0f * (x * x + y * y);
		float roll = (float) Math.atan2(sinr_cosp, cosr_cosp);
		
		// Pitch (y-axis rotation)
		float sinp = 2.0f * (w * y - z * x);
		float pitch= (Math.abs(sinp) >= 1) ? ((float) Math.copySign(Math.PI / 2, sinp)) : ((float) Math.asin(sinp)); // Use 90 degrees if out of range
		
		// Yaw (z-axis rotation)
		float siny_cosp = 2.0f * (w * z + x * y);
		float cosy_cosp = 1.0f - 2.0f * (y * y + z * z);
		float yaw = (float) Math.atan2(siny_cosp, cosy_cosp);
		
		return new float[]{roll, pitch, yaw};
	}
	
	public static Quaternionf translateDegrees(Quaternionf q) {
		return new Quaternionf(
				(float) Math.toDegrees(q.x),
				(float) Math.toDegrees(q.y),
				(float) Math.toDegrees(q.z),
				(float) Math.toDegrees(q.w));
	}
	
	public static Vector3f convertQuaternionToVector(Quaternionf q) {
		
		return new Vector3f(
				2 * (q.x * q.z - q.w * q.y),
				2 * (q.y * q.z + q.w * q.x),
				1 - 2 * (q.x * q.x + q.y * q.y)).normalize();
	}
	
	public static Location vector3ftoLocation(World world, Vector3f vec) {
		return new Location(world, vec.x, vec.y, vec.z);
	}
	
	public static Vector3f vectortovector3f(Vector vec) {
		return new Vector3f((float) vec.getX(), (float) vec.getY(), (float) vec.getZ());
	}

}