#include <string>
#include "com_mori5_itsecurity_cpp_CPPParserCaller.h"
#include <jni.h>

#include <iostream>
#include <fstream>
#include <list>
#include "vector"
#include <cstring>
#include <cstddef>
#include <sstream>

#include "main.h"

using namespace std;

JNIEXPORT jobject JNICALL Java_com_mori5_itsecurity_cpp_CPPParserCaller_parse(JNIEnv *env, jobject thisObject, jbyteArray caffArray)
{
    // Array to vector
    jsize sizeOfCaffArray = env->GetArrayLength(caffArray);
    std::vector<signed char> caffVector(sizeOfCaffArray);
    env->GetByteArrayRegion(caffArray, jsize{0}, sizeOfCaffArray, &caffVector[0]);

    // Call parser
    CreatorsImages image = CaffReader::readCaffFromFile(caffVector, false);

    // Convert objects

    // CreatorsImages

    jclass creatorsImagesClass = env->FindClass("com/mori5/itsecurity/cpp/CreatorsImages");
    jobject newCreatorsImages = env->AllocObject(creatorsImagesClass);

    jfieldID caffImageField = env->GetFieldID(creatorsImagesClass, "images", "Lcom/mori5/itsecurity/cpp/CaffImage;");
    jfieldID creatorString = env->GetFieldID(creatorsImagesClass, "creatorString", "Ljava/lang/String;");
    jfieldID errorMessage = env->GetFieldID(creatorsImagesClass, "error", "Ljava/lang/String;");
    jfieldID lengthOfCreator = env->GetFieldID(creatorsImagesClass, "lengthOfCreator", "J");
    jfieldID month = env->GetFieldID(creatorsImagesClass, "month", "B");
    jfieldID day = env->GetFieldID(creatorsImagesClass, "day", "B");
    jfieldID hour = env->GetFieldID(creatorsImagesClass, "hour", "B");
    jfieldID minute = env->GetFieldID(creatorsImagesClass, "minute", "B");
    jfieldID year = env->GetFieldID(creatorsImagesClass, "year", "S");

    env->SetLongField(newCreatorsImages, lengthOfCreator, (jlong)image.lengthOfCreator);
    env->SetByteField(newCreatorsImages, day, (jbyte)image.day);
    env->SetByteField(newCreatorsImages, month, (jbyte)image.month);
    env->SetByteField(newCreatorsImages, hour, (jbyte)image.hour);
    env->SetByteField(newCreatorsImages, minute, (jbyte)image.minute);
    env->SetShortField(newCreatorsImages, year, (jshort)image.year);

    uint64_t string_length_creator;
    string_length_creator = image.creatorString.length();
    char creator[string_length_creator + 1];
    strcpy(creator, image.creatorString.c_str());
    jstring jstrCreator = env->NewStringUTF(creator);
    env->SetObjectField(newCreatorsImages, creatorString, jstrCreator);

    uint64_t string_length_error;
    string_length_error = image.error.length();
    char error[string_length_error + 1];
    strcpy(error, image.error.c_str());
    jstring jstrError = env->NewStringUTF(error);
    env->SetObjectField(newCreatorsImages, errorMessage, jstrError);

    // Preview

    Image preview = image.images[0];

    jclass caffImageClass = env->FindClass("com/mori5/itsecurity/cpp/CaffImage");
    jobject newImage = env->AllocObject(caffImageClass);

    jfieldID content_size = env->GetFieldID(caffImageClass, "content_size", "J");
    jfieldID height = env->GetFieldID(caffImageClass, "height", "J");
    jfieldID width = env->GetFieldID(caffImageClass, "width", "J");
    jfieldID duration = env->GetFieldID(caffImageClass, "duration", "J");
    jfieldID pixels = env->GetFieldID(caffImageClass, "pixels", "Ljava/util/ArrayList;");
    jfieldID tags = env->GetFieldID(caffImageClass, "tags", "Ljava/lang/String;");
    jfieldID caption = env->GetFieldID(caffImageClass, "caption", "Ljava/lang/String;");

    env->SetLongField(newImage, content_size, (jlong)preview.content_size);
    env->SetLongField(newImage, height, (jlong)preview.height);
    env->SetLongField(newImage, width, (jlong)preview.width);
    env->SetLongField(newImage, duration, (jlong)preview.duration);

    uint64_t string_length_tag;
    string_length_tag = preview.tags.length();
    char preview_tags[string_length_tag + 1];
    strcpy(preview_tags, preview.tags.c_str());
    jstring jstrTags = env->NewStringUTF(preview_tags);
    env->SetObjectField(newImage, tags, jstrTags);

    uint64_t string_length_caption;
    string_length_caption = preview.caption.length();
    char preview_caption[string_length_caption + 1];
    strcpy(preview_caption, preview.caption.c_str());
    jstring jstrCaption = env->NewStringUTF(preview_caption);
    env->SetObjectField(newImage, caption, jstrCaption);

    // Pixels

    jclass clazz = env->FindClass("java/util/ArrayList");
    jobject obj = env->NewObject(clazz, env->GetMethodID(clazz, "<init>", "()V"));
    for (int n = 0; n < preview.pixels.size(); n++)
    {
        Pixel temp;
        temp = preview.pixels[n];

        jclass pixelClass = env->FindClass("Lcom/mori5/itsecurity/cpp/CaffPixel;");
        jobject newPixels = env->AllocObject(pixelClass);

        jfieldID red = env->GetFieldID(pixelClass, "red", "B");
        jfieldID green = env->GetFieldID(pixelClass, "green", "B");
        jfieldID blue = env->GetFieldID(pixelClass, "blue", "B");

        env->SetLongField(newPixels, red, (jbyte)temp.red);
        env->SetLongField(newPixels, green, (jbyte)temp.green);
        env->SetLongField(newPixels, blue, (jbyte)temp.blue);
        env->CallBooleanMethod(obj, env->GetMethodID(clazz, "add", "(Ljava/lang/Object;)Z"), newPixels);
    }

    env->SetObjectField(newImage, pixels, obj);
    env->SetObjectField(newCreatorsImages, caffImageField, newImage);

    return newCreatorsImages;
}

Image::Image(vector<Pixel> &pixels, uint64_t content_size, uint64_t width, uint64_t height, uint64_t duration, string tags, string caption)
{
    this->pixels = pixels;
    this->content_size = content_size;
    this->width = width;
    this->height = height;
    this->duration = duration;
    this->tags = tags;
    this->caption = caption;
}

CreatorsImages::CreatorsImages(uint16_t year, uint8_t month, uint8_t day, uint8_t hour, uint8_t minute, uint64_t lengthOfCreator, string &creatorString, vector<Image> &images)
{
    this->year = year;
    this->month = month;
    this->day = day;
    this->hour = hour;
    this->minute = minute;
    this->lengthOfCreator = lengthOfCreator;
    this->creatorString = creatorString;
    this->images = images;
}
CreatorsImages::CreatorsImages()
{
    this->error = "Bad Format";
}

namespace CaffReader
{

    CreatorsImages readCaffFromFile(vector<signed char> &buffer, const bool generateImages)
    {
        stringstream stream;
        stream.rdbuf()->pubsetbuf(reinterpret_cast<char *>(&buffer[0]), buffer.size());

        stream.seekg(0, ios::end);
        std::streampos totalFileSize;
        totalFileSize = stream.tellg();
        stream.seekg(0, ios::beg);

        uint8_t nextHeaderId = '\0';
        uint64_t nextHeaderLength = 0;
        uint64_t headerSizeCaff = 0;
        uint64_t headerSizeCiff = 0;
        uint64_t numberOfAnimatedCiffs = 0;
        uint16_t year = 0;
        uint8_t month = '\0';
        uint8_t day = '\0';
        uint8_t hour = '\0';
        uint8_t minute = '\0';
        uint64_t lengthOfCreator = 0;
        uint64_t duration = 0;
        uint64_t content_size = 0;
        uint64_t height = 0;
        uint64_t width = 0;
        char magicCaff[4];
        char magicCiff[4];
        string caption = "";
        string tags = "";
        string creatorString = "Unknown";
        vector<Image> images;

        while (!stream.eof())
        {
            stream.read((char *)&nextHeaderId, sizeof(uint8_t));
            stream.read((char *)&nextHeaderLength, sizeof(uint64_t));

            if (nextHeaderId == 1)
            {
                stream.read(magicCaff, sizeof(magicCaff));

                if (magicCaff[0] != 'C' && magicCaff[1] != 'A' && magicCaff[2] != 'F' && magicCaff[3] != 'F')
                {
                    return CreatorsImages();
                }

                stream.read((char *)&headerSizeCaff, sizeof(uint64_t));
                stream.read((char *)&numberOfAnimatedCiffs, sizeof(uint64_t));
            }
            else if (nextHeaderId == 2)
            {
                stream.read((char *)&year, sizeof(uint16_t));
                stream.read((char *)&month, sizeof(uint8_t));
                stream.read((char *)&day, sizeof(uint8_t));
                stream.read((char *)&hour, sizeof(uint8_t));
                stream.read((char *)&minute, sizeof(uint8_t));
                stream.read((char *)&lengthOfCreator, sizeof(uint64_t));
                if (lengthOfCreator != 0)
                {
                    char creator[lengthOfCreator];
                    stream.read(creator, sizeof(creator));
                    creatorString = creator;
                }
            }
            else if (nextHeaderId == 3)
            {
                tags = "";
                stream.read((char *)&duration, sizeof(uint64_t));

                uint64_t sizeBeforeCIFFReading = stream.tellg();

                //CIFF reading
                stream.read(magicCiff, sizeof(magicCiff));

                if (magicCiff[0] != 'C' && magicCiff[1] != 'I' && magicCiff[2] != 'F' && magicCiff[3] != 'F')
                {
                    return CreatorsImages();
                }

                stream.read((char *)&headerSizeCiff, sizeof(uint64_t));
                stream.read((char *)&content_size, sizeof(uint64_t));
                stream.read((char *)&width, sizeof(uint64_t));
                stream.read((char *)&height, sizeof(uint64_t));

                //Read string until \n newLine
                getline(stream, caption);

                //Tags read separated with \0 and then pixels
                uint64_t sizeOfTags = sizeBeforeCIFFReading + headerSizeCiff - stream.tellg();
                for (int i = 0; i < sizeOfTags; i++)
                {
                    char c;
                    stream.read((char *)&c, sizeof(char));
                    if (c == '\0')
                        c = ';';
                    tags += c;
                }

                //Read pixels
                vector<Pixel> pixels;
                for (int i = 0; i < width; i++)
                {
                    for (int j = 0; j < height; j++)
                    {
                        Pixel p;
                        stream.read((char *)&p, sizeof(Pixel));
                        pixels.push_back(p);
                    }
                }

                Image image(pixels, content_size, width, height, duration, tags, caption);
                images.push_back(image);
            }

            if (stream.tellg() == totalFileSize)
                break;
        }

        CreatorsImages creatorsImages(year, month, day, hour, minute, lengthOfCreator, creatorString, images);

        return creatorsImages;
    }
} // namespace CaffReader